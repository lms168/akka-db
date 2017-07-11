package com.exercise.unit1

import akka.actor.Actor
import akka.event.Logging

/**
  * Created by Administrator on 2017/7/12.
  */
class SaveStringActor extends Actor{
  val msgSeq = Seq[String]
  val log = Logging(context.system, this)
  override def receive: Receive = {
    case (msg:String) => {
      msgSeq +:  msg
      log.info(s"value is:$msg")
    }
    case o => log.info("received unknown message:{}",o)
  }
}
