// -*- mode: Scala;-*- 
// Filename:    sandbox.scala 
// Authors:     lgm                                                    
// Creation:    Thu Aug  9 16:36:53 2012 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.lift.lib.macros.sandbox

import scala.language.experimental.macros

import scala.reflect.makro.Context
import scala.reflect.runtime.{universe => ru}
import scala.reflect.ClassTag


abstract class BaseQueryableFactory{
  //def factory[S]( projection:ru.Expr[BaseQueryable[S]] ) : BaseQueryable[S]  
}

object Queryable extends BaseQueryableFactory{
  def apply[T](q:Queryable[T]) = new Queryable[T](q.expr_or_typetag) // TODO: make this a macro
  def apply[T:ru.TypeTag:ClassTag] = new Queryable[T](Right( (implicitly[ru.TypeTag[T]],implicitly[ClassTag[T]]) ))
  def factory[S]( projection:ru.Expr[BaseQueryable[S]] ) : Queryable[S] = {
    new Queryable[S]( Left(projection) )
  }
}

class QueryableUtils[C <: Context]( val context :C ) {
  import context.universe._
  def queryTree(queryable:Tree) = Apply( Select( (reify{QueryOps}).tree, newTermName("query") ), List(queryable) )
  object removeDoubleReify extends Transformer {
    def apply( tree:Tree ) = transform(tree)
    override def transform(tree: Tree): Tree = {
      super.transform {
        tree match {
          case  //Apply( // needed to account for ApplyToImplicitArgs
            Apply(TypeApply(Select(q, termname), _), reified::tail )
            //,_)
            if termname.toString == "factory"
            && q.tpe <:< typeOf[BaseQueryableFactory]
            => context.unreifyTree(reified)
          case //Apply(
            Apply(lhs@Select(q, termname), reified::tail )
            //,_)
            if termname.toString == "factory"
            && q.tpe <:< typeOf[BaseQueryableFactory]
            => context.unreifyTree(reified)
          case _ => tree
        }
      }
    }
  }
  def _select( queryable:Tree, method: String ) = removeDoubleReify(
    Select( queryTree(queryable), newTermName( method ))
  )
  def _apply( queryable:Tree, method: String, args:Tree* ) = removeDoubleReify(
    Apply( Select( queryTree(queryable), newTermName( method )), args.toList )
  )
  def _reifyTree[T]( tree:Tree ) = context.Expr[ru.Expr[T]](
      context.reifyTree( context.runtimeUniverse, EmptyTree, context.typeCheck(
        tree
      ).asInstanceOf[Tree]))
      
  def select[T]( queryable:Tree, method:String ) = _reifyTree[T]( _select(queryable, method) )
  def apply[T]( queryable:Tree, method: String, args:Tree* ) = _reifyTree[T]( _apply(queryable, method,args:_*) )
}

object QueryableMacros{
  private def _scalar_helper[C <: Context]( c:C )( name:String ) = {
    val utils = new QueryableUtils[c.type](c)
    val reifiedExpression = utils.select[Int]( c.prefix.tree, name )
    c.universe.reify{ new QueryableValue( reifiedExpression.splice ) }
  }
  def length
      (c: scala.reflect.makro.Context)
      : c.Expr[QueryableValue[Int]] = _scalar_helper[c.type]( c )( "length" )

  private def _helper[C <: Context,S:c.TypeTag]( c:C )( name:String, projection:c.Expr[_] ) = {
    val utils = new QueryableUtils[c.type](c)
    val reifiedExpression = utils.apply[Queryable[S]]( c.prefix.tree, name, projection.tree )
    c.universe.reify{ Queryable.factory[S]( reifiedExpression.splice )}
  }

  def map[T:c.TypeTag, S:c.TypeTag]
  (c: scala.reflect.makro.Context)
  (projection: c.Expr[T => S]): c.Expr[com.biosimilarity.lift.lib.macros.sandbox.Queryable[S]] = _helper[c.type,S]( c )( "map", projection )
  def flatMap[T:c.TypeTag, S:c.TypeTag]
  (c: scala.reflect.makro.Context)
  (projection: c.Expr[T => Queryable[S]]): c.Expr[com.biosimilarity.lift.lib.macros.sandbox.Queryable[S]] = _helper[c.type,S]( c )( "flatMap", projection )
  def filter[T:c.TypeTag]
  (c: scala.reflect.makro.Context)
  (projection: c.Expr[T => Boolean]): c.Expr[com.biosimilarity.lift.lib.macros.sandbox.Queryable[T]] = _helper[c.type,T]( c )( "filter", projection )
}

class QueryableValue[T]( val value : ru.Expr[T] )
abstract class BaseQueryable [T]( val expr_or_typetag : Either[ ru.Expr[_], (ru.TypeTag[_],ClassTag[_]) ] ){
  def queryable = this
}

object QueryOps{
  def query[T]( queryable:BaseQueryable[T] ) : QueryOps[T] = ???
}
class QueryOps[T]{
  def map[S]( projection: T => S ) : BaseQueryable[S] = ???
  def flatMap[S]( projection: T => BaseQueryable[S] ) : BaseQueryable[S] = ???
  def filter( projection: T => Boolean ) : BaseQueryable[T] = ???
  def length[S] : Int = ???  
}

class Queryable[T]( expr_or_typetag : Either[ ru.Expr[_], (ru.TypeTag[_],ClassTag[_]) ] ) extends BaseQueryable[T]( expr_or_typetag ){
  def map[S]( projection: T => S ) : Queryable[S] = macro QueryableMacros.map[T,S]
  def flatMap[S]( projection: T => Queryable[S] ) : Queryable[S] = macro QueryableMacros.flatMap[T,S]
  def filter( projection: T => Boolean ) : Queryable[T] = macro QueryableMacros.filter[T]
  def withFilter( projection: T => Boolean ) : Queryable[T] = macro QueryableMacros.filter[T]  
  def length : QueryableValue[Int]  = macro QueryableMacros.length
}

/* ------------------------------------------------------------------------ */
/* This is work in progress toward a scala rendering of co-do               */
/* notation. Currently, the types are all wrong because the macros are      */
/* simply identity macros -- to hold the place of what should be there      */
/* eventually.                                                              */
/* ------------------------------------------------------------------------ */

object CoQueryableMacros {
  def coFlatMapM[T:c.TypeTag, S:c.TypeTag](
    c : scala.reflect.makro.Context
  )(
    coProjection : c.Expr[CoQueryable[T] => S]
  ) : c.Expr[CoQueryable[T] => S] = {
    c.universe.reify{ coProjection.splice }
  }

  def filter[T:c.TypeTag](
    c : scala.reflect.makro.Context
  )(
    coProjection : c.Expr[T => Boolean]
  ) : c.Expr[T => Boolean] = {
    c.universe.reify{ coProjection.splice }
  }

  //def coFlatMap[T,S]( coProjection : CoQueryable[T] => S ) : CoQueryable[T] => S = macro coFlatMapM[T,S]
  
}

class CoQueryableValue[T]( val value : ru.Expr[T] )
abstract class BaseCoQueryable [T]( val expr_or_typetag : Either[ ru.Expr[_], (ru.TypeTag[_],ClassTag[_]) ] ){
  def queryable = this
}

object CoQueryOps{
  def query[T]( queryable:BaseCoQueryable[T] ) : QueryOps[T] = ???
}
class CoQueryOps[T]{
  def coFlatMap[S]( coProjection: T => BaseCoQueryable[S] ) : BaseCoQueryable[S] = ???
  def filter( coProjection: T => Boolean ) : BaseCoQueryable[T] = ???
}

class CoQueryable[T]( expr_or_typetag : Either[ ru.Expr[_], (ru.TypeTag[_],ClassTag[_]) ] ) extends BaseCoQueryable[T]( expr_or_typetag ){
  def coFlatMap[S]( coProjection : CoQueryable[T] => S ) : CoQueryable[T] => S = macro CoQueryableMacros.coFlatMapM[T,S]
  def filter( coProjection : T => Boolean ) : T => Boolean = macro CoQueryableMacros.filter[T]
  def withFilter( coProjection : T => Boolean ) : T => Boolean = macro CoQueryableMacros.filter[T]  
}
