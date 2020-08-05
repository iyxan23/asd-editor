package com.ihsan.asdeditor.colorscheme

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import com.ihsan.asdeditor.rule.AnnotationRule
import com.ihsan.asdeditor.rule.ClassKeywordRule
import com.ihsan.asdeditor.rule.FinalKeywordRule
import com.ihsan.asdeditor.rule.ImportKeywordRule
import com.ihsan.asdeditor.rule.PackageKeywordRule
import com.ihsan.asdeditor.rule.ReturnKeywordRule
import com.ihsan.asdeditor.rule.StaticKeywordRule
import com.ihsan.asdeditor.rule.TypeKeywordRule
import com.ihsan.asdeditor.rule.VisibilityKeywordRule
import com.ihsan.asdeditor.rule.AllKeywordsRule
import com.ihsan.asdeditor.rule.CommentRule
import de.markusressel.kodehighlighter.core.StyleFactory
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme
import de.markusressel.kodehighlighter.core.rule.LanguageRule

/**
 * A dark color scheme for java
 */
class DarkBackgroundColorScheme : ColorScheme {

    override fun getStyles(type: LanguageRule): Set<StyleFactory> {
        return when (type) {
            is ImportKeywordRule,
            is PackageKeywordRule,
            is ClassKeywordRule,
            is TypeKeywordRule,
            is StaticKeywordRule,
            is ReturnKeywordRule,
            is FinalKeywordRule,
            is AllKeywordsRule,
            is VisibilityKeywordRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#F92672")) }
            }
            is AnnotationRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FBC02D")) }
            }
            is CommentRule -> {
                setOf { ForegroundColorSpan(Color.parseColor("#75715E")) }
            }
            else -> {
                setOf { ForegroundColorSpan(Color.parseColor("#FFFFFF")) }
            }
        }
    }

}