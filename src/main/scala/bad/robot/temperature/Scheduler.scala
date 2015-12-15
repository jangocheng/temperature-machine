package bad.robot.temperature

import java.util.concurrent.{ScheduledExecutorService, ScheduledFuture}

import scala.collection.JavaConverters._
import scala.concurrent.duration.Duration

object Scheduler {
  def apply(frequency: Duration, executor: ScheduledExecutorService) = new Scheduler(frequency, executor)
  def apply(executor: ScheduledExecutorService) = new Scheduler(Duration(30, "seconds"), executor)
}

class Scheduler(frequency: Duration, executor: ScheduledExecutorService) {

  def start(tasks: Runnable*): List[ScheduledFuture[_]] = {
    tasks.map(task => executor.scheduleWithFixedDelay(task, 0, frequency.length, frequency.unit)).toList
  }

  def cancel(tasks: List[ScheduledFuture[_]]) {
    tasks.foreach(task => task.cancel(true))
  }

  def stop: List[Runnable] = {
    val tasks = executor.shutdownNow.asScala
    tasks.foreach(task => task.asInstanceOf[ScheduledFuture[_]].cancel(true))
    tasks.toList
  }

}