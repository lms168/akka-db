package com.zzcm

import com.typesafe.config._

import scala.io.Source
import scala.concurrent.Await
import scala.concurrent.duration._

import slick.driver.MySQLDriver.api._

import example.bean.KeyValue

object App extends Greeting with App {
  println(greeting)

  //read file 1M
  val source = Source.fromFile("/home/zhangdi/NetDisk/delete/z20170523/szdsp-kv/split/D1.txt","UTF-8") 
  
  val lineIterator = source.getLines
  
  /*
  val length = 90
  for (l <- lineIterator) {
    if (l.length > length) println(l.substring(length)) else println(l)
  }
  */

  val subLength = 90
  val rows = lineIterator.filter(_.length >subLength).map(_.substring(subLength))

  val kvs = rows.map(_.split(" ")).filter(_.length == 3).map(_.dropRight(1)).map(x => ((x(0).split(":")(1) + ":" + x(0).split(":")(2),x(1).split(":")(1) ))).map(x => new KeyValue(x._1, x._2.toLong))

  val config = ConfigFactory.load()
  //DataBase Config
  val db = Database.forURL(
    config.getString("db.conf.url"), //"jdbc:mysql://192.168.0.223:3306/iadbigdata",
    config.getString("db.conf.user"), //"zzmanager",
    config.getString("db.conf.pwd"), //"iadMOB-2013@0622)",
    driver= config.getString("db.conf.driver") //"com.mysql.jdbc.Driver"
  )

  //Insert Data Mysql
  try{
  
     val obj= db.run(sql"select count(id) from UNStatesMsg_PV_MIN".as[Int].head)
     val result = Await.result(obj, 2.seconds)
     println(result)


    val num = kvs.map( x => {
      val param = x.reset
      println(param)
      val insertRow = sql"INSERT INTO `UNStatesMsg_PV_MIN` (`appId`, `date`, `hour`, `minute`, `dimension_one_key`, `dimension_one_value`, `operator`, `value`) VALUES (#${param("appId")}, #${param("date")}, #${param("hour")}, '20', 'ad', '459', 'QUERY', '1')".as[Int]
      Await.result(db.run(insertRow), 2.seconds)
    }).size



  }catch{
    case e: Exception => println(e)
  }

  println("... End ...")

}

trait Greeting {
  lazy val greeting: String = "hello"
}
