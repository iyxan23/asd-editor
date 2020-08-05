package com.ihsan.asdeditor

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule
import com.ihsan.asdeditor.colorscheme.DarkBackgroundColorScheme
import com.ihsan.asdeditor.rule.AnnotationRule
import com.ihsan.asdeditor.rule.ClassKeywordRule
import com.ihsan.asdeditor.rule.FinalKeywordRule
import com.ihsan.asdeditor.rule.ImportKeywordRule
import com.ihsan.asdeditor.rule.PackageKeywordRule
import com.ihsan.asdeditor.rule.ReturnKeywordRule
import com.ihsan.asdeditor.rule.StaticKeywordRule
import com.ihsan.asdeditor.rule.TypeKeywordRule
import com.ihsan.asdeditor.rule.VisibilityKeywordRule
import com.ihsan.asdeditor.rule.CommentRule

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
                VisibilityKeywordRule(),
                CommentRule())
    }

}