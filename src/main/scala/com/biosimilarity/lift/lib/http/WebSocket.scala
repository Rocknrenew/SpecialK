// -*- mode: Scala;-*- 
// Filename:    WebSocket.scala 
// Authors:     lgm                                                    
// Creation:    Fri Dec  2 14:28:24 2011 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.lift.lib.http

import org.eclipse.jetty.websocket.WebSocket
import com.biosimilarity.lift.lib.kvdbJSON.KVDBJSONAPIDispatcher
import java.net.URI

trait SocketConnectionPair {
  def requestQueue : Iterator[(URI,String)] 
  def responseConnection : WebSocket.Connection
}

// because I couldn't find a Queue that worked inside a for loop in the standard scala library
// we use Iterator[String] because it is traversable only once which is a good match to a Queue that pops as you read it
trait RequestQueue extends Iterator[(URI,String)] { 
  def +=(msg: (URI,String)) = put(msg)
  def put(msg: (URI,String))
}

case class SCP(
  requestQueue : Iterator[(URI,String)]
  , responseConnection : WebSocket.Connection
) extends SocketConnectionPair

case class QueuingWebSocket( 
  requestQueue : RequestQueue
  , uri: URI
  , openCB : SocketConnectionPair => Unit
  , closeCB : () => Unit
) extends WebSocket 
     with WebSocket.OnTextMessage
{ 
  
  override def onOpen(
    wsConnection: WebSocket.Connection
  ) : Unit = {
    println( "in onOpen with " + wsConnection )
    openCB( SCP( requestQueue, wsConnection ) )    
    println( "onOpen complete" )
  }
  
  override def onClose(
    closeCode: Int,
    message: String
  ) : Unit = {
    println( "in onClose with " + closeCode + " and " + message )
    closeCB()
  }
  
  override def onMessage(
    message: String
  ) : Unit = {
    println("adding message to queue (of size " + requestQueue.size + ")  " + message)
    requestQueue += (uri,message)
  }    
}
