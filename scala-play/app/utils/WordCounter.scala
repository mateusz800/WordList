package utils

import scala.collection.immutable.ListMap

object WordCounter {
  def countWords(lines: LazyList[String]) = {
    ListMap.from(
      lines.flatMap(line => """(?i)[a-z]+""".r.findAllIn(line.toLowerCase()))
        .groupMap(identity)(_ => 1)
        .map(entry => (entry._1, entry._2.sum))
        .toList
        .sortBy(entry => (-entry._2, entry._1))
    )
  }
}
