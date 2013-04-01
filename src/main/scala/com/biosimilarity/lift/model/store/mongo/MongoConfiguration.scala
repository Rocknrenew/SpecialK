// -*- mode: Scala;-*- 
// Filename:    MongoConfiguration.scala 
// Authors:     lgm                                                    
// Creation:    Sat Feb 23 02:19:17 2013 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.lift.model.store.mongo

import com.biosimilarity.lift.model.store.URIFromConfigurationT
import com.biosimilarity.lift.model.ApplicationDefaults
import com.biosimilarity.lift.model.MongoDefaults

import java.net.URI

object MongoConfigInfo
extends MongoStoreConfiguration
with URIFromConfigurationT {  
  override def configFileName: Option[String] = Some("kvdb_mongo.conf")
  override def configurationDefaults: ConfigurationDefaults =
    MongoDefaults.asInstanceOf[ConfigurationDefaults]
  override def defaultSchemeFromConfiguration : String = 
    _sessionURIFromConfiguration.getScheme
  override def defaultPathFromConfiguration : String = 
    _defaultDB

  lazy val _sessionURIFromConfiguration : URI =
    getSessionURIFromConfiguration
  lazy val _defaultDB = defaultDB
}

