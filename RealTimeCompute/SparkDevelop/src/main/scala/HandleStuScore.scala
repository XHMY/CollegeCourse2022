import org.apache.spark.SparkContext

object HandleStuScore {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local", "HandleStuScore")
    val lines = sc.textFile("C:\\Users\\zyf6\\Documents\\GitHub\\CollegeCourse2022\\RealTimeCompute\\SparkDevelop\\src\\main\\resources\\scores.txt")
    val stu_score = lines.map(line => {
      val fields = line.split(" ")
      for i in 0 until fields.length
      

    }

  }

}
