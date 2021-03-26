package org.jmisb.maven;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ClassModelEntry {

    private int number;
    private String name;
    private String typeName;
    private List<ArrayDimension> arrayDimensions = new ArrayList<>();
    private boolean list = false;
    private Double minValue = null;
    private Double maxValue = null;
    private Double resolution = null;
    private Integer minLength = null;
    private Integer maxLength = null;
    private String units = "";
    private ClassModel parent = null;
    private boolean deprecated = false;
    private boolean ref = false;
    private final Stack<String> unitStack = new Stack<>();

    public String getPackageName() {
        return parent.getPackageName();
    }

    public String getDocument() {
        return parent.getDocument();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getParentName() {
        return parent.getName();
    }

    public String getNamespacedName() {
        return parent.getName() + "_" + getNameSentenceCase();
    }

    public String getNamespacedQualifiedName() {
        return parent.getTypePackage(parent.getName())
                + "."
                + parent.getName()
                + "_"
                + getNameSentenceCase();
    }

    public String getNameSentenceCase() {
        return getName().substring(0, 1).toUpperCase() + getName().substring(1);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * Get the corresponding primitive type for this entry.
     *
     * <p>This might be "double" for a Real type or "long" for UInt or Integer types.
     *
     * @return a String containing the name of a primitive Java type
     */
    public String getPrimitiveType() {
        switch (getTypeName()) {
            case "Integer":
                return "long";
            case "UInt":
                return "long";
            default:
                throw new UnsupportedOperationException("Not yet implemented:" + getTypeName());
        }
    }

    /**
     * Get as description for the corresponding primitive type for this entry.
     *
     * <p>This might be "a floating point value" for a Real type or "an unsigned integer" for a UInt
     * type.
     *
     * @return a String containing the description of the type
     */
    public String getTypeDescription() {
        switch (getTypeName()) {
            case "Integer":
                return "a signed integer";
            case "UInt":
                return "an unsigned integer";
            default:
                throw new UnsupportedOperationException("Not yet implemented:" + getTypeName());
        }
    }

    public String getQualifiedTypeName() {
        return parent.getTypePackage(typeName) + "." + typeName;
    }

    public String getQualifiedListTypeName() {
        return parent.getTypePackage(typeName) + ".ListOf" + typeName;
    }

    public void setTypeName(String typeName) {
        if (typeName.equals("Object")) {
            this.typeName = "Objct";
        } else {
            this.typeName = typeName;
        }
    }

    public boolean isArray() {
        return (!arrayDimensions.isEmpty());
    }

    public boolean isArray1D() {
        return arrayDimensions.size() == 1;
    }

    public boolean isArray2D() {
        return arrayDimensions.size() == 2;
    }

    public void addArrayDimension(ArrayDimension arrayDimension) {
        this.arrayDimensions.add(arrayDimension);
    }

    public Integer getArrayDimensionSize(int dim) {
        if (dim < arrayDimensions.size()) {
            return this.arrayDimensions.get(dim).getMaxLength();
        } else {
            return null;
        }
    }

    public boolean isList() {
        return list;
    }

    public void setIsList(boolean list) {
        this.list = list;
    }

    public Double getMinValue() {
        return minValue;
    }

    public boolean isPrimitive() {
        return typeName.equals("Real")
                || typeName.equals("String")
                || typeName.equals("Integer")
                || typeName.equals("UInt")
                || typeName.equals("Boolean")
                || typeName.equals("Tuple");
    }

    public boolean isRef() {
        return ref;
    }

    public void setIsRef(boolean ref) {
        this.ref = ref;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getResolution() {
        return resolution;
    }

    public void setResolution(Double resolution) {
        this.resolution = resolution;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getUnits() {
        if (units.equalsIgnoreCase("None")) {
            return null;
        }
        return units;
    }

    public String getEscapedUnits() {
        if (units.equals("%")) {
            return "%%";
        }
        return units;
    }

    public void addUnitSymbol(String symbol) {
        this.unitStack.push(symbol);
    }

    public void addUnitWord(String word) {
        this.units = this.units.concat(word);
        if (!this.unitStack.isEmpty()) {
            String unit = unitStack.pop();
            this.units = this.units.concat(unit);
        }
    }

    void setParent(ClassModel classModel) {
        this.parent = classModel;
    }

    void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public boolean isDeprecated() {
        return deprecated;
    }
}
