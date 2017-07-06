package example

import com.redis._
import com.typesafe.config.ConfigFactory


/**
  * Created by zhangdi on 17-6-8.
  */
object SentCheckRedis extends App{

  println("Init config")

  val config = ConfigFactory.load()

  val r = new RedisClient(config.getString("remote.src.host"), config.getInt("remote.src.port"), config.getInt("remote.src.db"))

  val list = r.keys("*").get

  list.map( x => {
    println(r.get(x.get))
  })

}
