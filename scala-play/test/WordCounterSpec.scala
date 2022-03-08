import org.scalatestplus.play.PlaySpec
import utils.WordCounter.countWords

class WordCounterSpec extends PlaySpec{
  "Words 'Ala', 'red' 'backpack', " must {
    "occurs 2 times" in {
      val wordOccurrences = countWords(LazyList("Ala have a red backpack. A red backpack is owned by Ala"))
      wordOccurrences.get("ala") mustBe Some(2)
      wordOccurrences.get("red") mustBe Some(2)
      wordOccurrences.get("backpack") mustBe Some(2)
    }
  }

  "Words list" must {
    "be sorted by occurrences and then alphabetical" in {
      val wordOccurrences = countWords(LazyList("A red backpack is owned by Ala. Ala have a red backpack."))
      val keys = wordOccurrences.keySet.toList
      keys.head mustBe "a"
      keys(1) mustBe "ala"
      keys(2) mustBe "backpack"
      keys(3) mustBe "red"
    }
  }
}
