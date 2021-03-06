/**
 * Happy Panda Example for DataFrames. Computes the % of happy pandas. Very contrived.
 */
package com.highperformancespark.examples.streaming

import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat

import org.apache.spark._
import org.apache.spark.rdd.RDD

//tag::DStreamImports[]
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream._
//end::DStreamImports[]

class DStreamExamples(sc: SparkContext, ssc: StreamingContext) {
  def makeStreamingContext() = {
    //tag::ssc[]
    val batchInterval = Seconds(1)
    new StreamingContext(sc, batchInterval)
    //end::ssc[]
  }

  def fileAPIExample(path: String) = {
    //tag::file[]
    ssc.fileStream[LongWritable, Text, TextInputFormat](path)
    //end::file[]
  }

  def repartition(dstream: DStream[_]) = {
    //tag::repartition[]
    dstream.repartition(20)
    //end::repartition[]
  }

  def foreachSaveSequence(target: String, dstream: DStream[(Long, String)]) = {
    //tag::foreachSave[]
    dstream.foreachRDD{(rdd, window) =>
      rdd.saveAsSequenceFile(target + window)
    }
    //end::foreachSave[]
  }
}
