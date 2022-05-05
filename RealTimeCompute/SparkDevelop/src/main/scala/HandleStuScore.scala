import org.apache.spark.SparkContext

object HandleStuScore {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local", "HandleStuScore")
    val lines = sc.textFile("src/main/resources/scores.txt")
    // line: "score1 ..."

    val pairs = lines.map(line => for(i <- line.split(" ")) yield i.toFloat)
    // pairs: Array[Array[Float]] = Array(Array(1.0, ...), ...)

    val stu_num = lines.count()
    val avg_result = pairs.reduce((x, y) => x.zip(y).map(x => x._1 + x._2)).map(x => x/stu_num)
    val max_result = pairs.reduce((x, y) => x.zip(y).map(x => if(x._1 > x._2) x._1 else x._2))
    val min_result = pairs.reduce((x, y) => x.zip(y).map(x => if(x._1 < x._2) x._1 else x._2))
    // result: Array[Float] = Array(2.0, ...)

    println("Students Numbers: " + stu_num)
    avg_result.foreach(x => print("Avg. "+ x + "  "))
    println()
    max_result.foreach(x => print("Max. "+ x + "  "))
    println()
    min_result.foreach(x => print("Min. "+ x + "  "))
    println()
  }

}
