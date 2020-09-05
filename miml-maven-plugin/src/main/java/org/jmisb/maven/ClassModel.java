package org.jmisb.maven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassModel {
    private String packageNameBase;
    private String name;
    private String includes;
    private String document;
    private List<ClassModelEntry> entries = new ArrayList<>();
    private Map<String, String> packageLookup = new HashMap<>();
    private boolean topLevel = false;

    public String getPackageName() {
        return packageNameBase + "." + document.toLowerCase();
    }

    public void setPackageNameBase(String packageNameBase) {
        this.packageNameBase = packageNameBase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIncludes() {
        return includes;
    }

    public void setIncludes(String includes) {
        this.includes = includes;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public boolean isTopLevel() {
        return topLevel;
    }

    public void setTopLevel(boolean topLevel) {
        this.topLevel = topLevel;
    }

    public List<ClassModelEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ClassModelEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(ClassModelEntry entry) {
        this.entries.add(entry);
    }

    void parseClassLine(String line) {
        String[] lineParts = line.split(" ");
        setName(lineParts[1].trim());
        if (line.contains("includes")) {
            setIncludes(lineParts[3].trim());
            // TODO: include Base
        }
    }

    public void setPackageLookup(Map<String, String> lookups) {
        packageLookup.putAll(lookups);
    }

    String getTypePackage(String listItemType) {
        return packageLookup.get(listItemType);
    }
}