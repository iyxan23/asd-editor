package com.ihsan.asdeditor

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import com.ihsan.asdeditor.colorscheme.DarkBackgroundColorScheme
import com.ihsan.asdeditor.rule.*

/**
 * Java language rule book
 */
class JavaRuleBook : LanguageRuleBook {

    override val defaultColorScheme: ColorScheme = DarkBackgroundColorScheme()

    override fun getRules(): Set<LanguageRule> {
        return setOf(
                PackageKeywordRule(),
                ImportKeywordRule(),
                ClassKeywordRule(),
                AnnotationRule(),
                TypeKeywordRule(),
                FinalKeywordRule(),
                StaticKeywordRule(),
                ReturnKeywordRule(),
                AllKeywordsRule(),
                VisibilityKeywordRule(),
                CommentRule())
    }

}