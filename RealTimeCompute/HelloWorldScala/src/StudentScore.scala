import scala.Double.{MaxValue, MinValue}
import scala.io.StdIn.{readDouble, readInt}
import scala.math.{max, min}

object StudentScore {
  def main(args: Array[String]): Unit = {
    //    println("Please enter classes number:")
    val m = readInt() // m 个班级
    var stu_cnt = 0
    var all_sum = 0.0
    var all_min = MaxValue
    var all_max = MinValue
    for (i <- 0 until m) {
      val n = readInt() // n 个学生
      var class_sum = 0.0
      var class_min = MaxValue
      var class_max = MinValue
      for (j <- 0 until n) {
        val s = readDouble()
        class_sum += s
        class_min = min(class_min, s)
        class_max = max(class_max, s)
      }
      System.out.printf("Class %d - avg %.2f  min %.2f  max %.2f\n", i, class_sum / n, class_min, class_max)
      all_sum += class_sum
      all_min = min(all_min, class_min)
      all_max = max(all_max, class_max)
      stu_cnt += n
    }
    System.out.printf("Grade - avg %.2f  min %.2f  max %.2f\n", all_sum / stu_cnt, all_min, all_max)
  }
}
