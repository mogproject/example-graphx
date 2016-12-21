name := "example-graphx"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.0.2",
  "org.apache.spark" %% "spark-graphx" % "2.0.2"
)

initialCommands  += """
  import org.apache.spark._
  import org.apache.spark.graphx._
  import org.apache.spark.storage._
  val conf = new SparkConf().setMaster(s"local[2]").setAppName("example-graphx")
  implicit val sc = new SparkContext(conf)
"""