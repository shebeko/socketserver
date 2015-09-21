@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(value = CharacterAdapter.class, type = Character.class)
})
package ru.bis.integration.kase.domain.next;
import javax.xml.bind.annotation.adapters.*;
import ru.bis.integration.kase.util.CharacterAdapter;