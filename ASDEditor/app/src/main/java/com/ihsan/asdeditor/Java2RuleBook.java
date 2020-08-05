package com.ihsan.asdeditor;

import com.ihsan.asdeditor.colorscheme.DarkBackgroundColorScheme;
import com.ihsan.asdeditor.rule.AnnotationRule;
import com.ihsan.asdeditor.rule.ClassKeywordRule;
import com.ihsan.asdeditor.rule.CommentRule;
import com.ihsan.asdeditor.rule.FinalKeywordRule;
import com.ihsan.asdeditor.rule.ImportKeywordRule;
import com.ihsan.asdeditor.rule.PackageKeywordRule;
import com.ihsan.asdeditor.rule.ReturnKeywordRule;
import com.ihsan.asdeditor.rule.StaticKeywordRule;
import com.ihsan.asdeditor.rule.TypeKeywordRule;
import com.ihsan.asdeditor.rule.VisibilityKeywordRule;
import de.markusressel.kodehighlighter.core.LanguageRuleBook;
import de.markusressel.kodehighlighter.core.LanguageRuleBook.DefaultImpls;
import de.markusressel.kodehighlighter.core.colorscheme.ColorScheme;
import de.markusressel.kodehighlighter.core.rule.LanguageRule;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"},
        d2 = {"Lcom/ihsan/asdeditor/JavaRuleBook;", "Lde/markusressel/kodehighlighter/core/LanguageRuleBook;", "()V", "defaultColorScheme", "Lde/markusressel/kodehighlighter/core/colorscheme/ColorScheme;", "getDefaultColorScheme", "()Lde/markusressel/kodehighlighter/core/colorscheme/ColorScheme;", "getRules", "", "Lde/markusressel/kodehighlighter/core/rule/LanguageRule;", "app"}
)
public final class Java2RuleBook implements LanguageRuleBook {
    @NotNull
    private final ColorScheme defaultColorScheme = (ColorScheme)(new DarkBackgroundColorScheme());

    @NotNull
    public ColorScheme getDefaultColorScheme() {
        return this.defaultColorScheme;
    }

    @NotNull
    public Set getRules() {
        return SetsKt.setOf((LanguageRule)(new PackageKeywordRule()), (LanguageRule)(new ImportKeywordRule()), (LanguageRule)(new ClassKeywordRule()), (LanguageRule)(new AnnotationRule()), (LanguageRule)(new TypeKeywordRule()), (LanguageRule)(new FinalKeywordRule()), (LanguageRule)(new StaticKeywordRule()), (LanguageRule)(new ReturnKeywordRule()), (LanguageRule)(new VisibilityKeywordRule()), (LanguageRule)(new CommentRule()));
    }

    @Nullable
    public Object createHighlighting(@NotNull CharSequence charSequence, @NotNull Continuation var2) {
        return DefaultImpls.createHighlighting(this, charSequence, var2);
    }
}
