package ru.bis.integration.kase.util;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author shds
 *
 */
public class CharacterAdapter extends XmlAdapter<String, Character> {

    @Override
    public Character unmarshal(String v) throws Exception {
        if (v.length() != 1) throw new IllegalArgumentException("String v cannot be converted to char");
        return Character.valueOf(v.charAt(0));
    }

    @Override
    public String marshal(Character v) throws Exception {
        if (v.charValue()=='\u0000')
            return "";
        else return String.valueOf(v.charValue());
    }
}