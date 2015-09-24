package example

import ccg.{NP, N, X, CcgCat}
import parser.{Else, SemanticParser, ParserDict}
import semantics.Ignored

object CcgBankParser extends SemanticParser[CcgCat](CcgBankLexicon.lexicon) {
  def main(args: Array[String]): Unit = {
    val input = args.mkString(" ")
    val result = parse(input)
    val output = result.bestParse.map(_.semantic) match {
      case Some(Ignored(parse)) => parse
      case _ => "(failed to parse)"
    }

    println(s"Input: $input")
    println(s"Highest-scoring parse: $output")

    if (result.bestParse.isDefined) {
      println(result.bestParse.map(_.toStringHelp(withSemantics = false)))
    }
  }
}

object CcgBankLexicon {
  val lexicon = ParserDict.fromOldCcgBankLexicon("data/lexicon.wsj02-21") +
    (Else -> Seq(N % 0.9, X % 0.1))  // unrecognized terms are probably nouns, but could be anything
}