package org.jmisb.maven;

/** Shared code between ClassModel and EnumerationModel. */
public class AbstractModel {

    private String packageNameBase;
    private String name;
    private String document;

    public String getPackageName() {
        if (packageNameBase == null) {
            return null;
        }
        return packageNameBase + "." + document.toLowerCase();
    }

    void setPackageNameBase(String packageNameBase) {
        this.packageNameBase = packageNameBase;
    }

    public String getName() {
        if (name.equals("Object")) {
            return "PayloadObject";
        }
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    void setDocument(String document) {
        this.document = document;
    }

    String getDirectoryPackagePart() {
        return getDocument().toLowerCase() + "/";
    }
}
