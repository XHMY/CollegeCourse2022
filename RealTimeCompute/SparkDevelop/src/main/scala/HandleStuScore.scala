import org.apache.spark.SparkContext

object HandleStuScore {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local", "HandleStuScore")
    val lines = sc.textFile("C:\\Users\\zyf6\\Documents\\GitHub\\CollegeCourse2022\\RealTimeCompute\\SparkDevelop\\src\\main\\resources\\scores.txt")
    // line: "score1 score2 score3 score4 score5"

    val pairs = lines.map(line => for(i <- line.split(" ")) yield i.toFloat)
    // pairs: Array[Array[Float]] = Array(Array(1.0, 2.0, 3.0, 4.0, 5.0), ...)

    val avg_result = pairs.reduce((x, y) => x.zip(y).map(x => x._1 + x._2))
    val max_result = pairs.reduce((x, y) => x.zip(y).map(x => if(x._1 > x._2) x._1 else x._2))
    val min_result = pairs.reduce((x, y) => x.zip(y).map(x => if(x._1 < x._2) x._1 else x._2))
    // result: Array[Float] = Array(2.0, ...)

    avg_result.foreach(x => println("Avg. "+ x/lines.count()))
    max_result.foreach(x => println("Max. "+ x))
    min_result.foreach(x => println("Min. "+ x))

  }

}
