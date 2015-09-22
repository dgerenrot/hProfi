package com.websushibar.hprofpersist.hprofentries.dumpSubtags;

import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.websushibar.hprofpersist.hprofentries.dumpSubtags.BasicTypeTag.getByValue;
import static com.websushibar.hprofpersist.hprofentries.dumpSubtags.BasicTypeTag.readEntryValue;


@Document(collection="hprofClassDumps")
public class  ClassDump  extends AbstractObjDump {

    private IDField superClassObjId;
    private IDField classLoaderObjId;
    private IDField signersObjId;
    private IDField protectionDomainObjId;
    private IDField reserved1;
    private IDField reserved2;
    private int instanceSizeBytesSigned;

    private short nConstants;
    private short nStaticFields;
    private short nInstanceFields;

    private List<Constant> constants;
    private List<StaticField> staticFields;
    private List<InstanceField> instanceFields;

    public IDField getSuperClassObjId() {
        return superClassObjId;
    }

    public IDField getClassLoaderObjId() {
        return classLoaderObjId;
    }

    public IDField getSignersObjId() {
        return signersObjId;
    }

    public IDField getProtectionDomainObjId() {
        return protectionDomainObjId;
    }

    public IDField getReserved1() {
        return reserved1;
    }

    public IDField getReserved2() {
        return reserved2;
    }

    public int getInstanceSizeBytesSigned() {
        return instanceSizeBytesSigned;
    }

    public Collection<Constant> getConstants() {
        return constants;
    }

    public Collection<StaticField> getStaticFields() {
        return staticFields;
    }

    public Collection<InstanceField> getInstanceFields() {
        return instanceFields;
    }

    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        super.readBody(l);
        superClassObjId = l.readId();
        classLoaderObjId = l.readId();
        signersObjId = l.readId();
        protectionDomainObjId = l.readId();
        reserved1 = l.readId();
        reserved2 = l.readId();
        instanceSizeBytesSigned = l.readInt();

        nConstants = l.readShort();
        constants = new ArrayList<Constant>(nConstants);

        for (int i = 0; i < nConstants; i++) {
            Constant c = readConstant(l);
            constants.add(c);
        }


        nStaticFields = l.readShort();
        staticFields = new ArrayList<StaticField>(nStaticFields);

        for (int i = 0; i < nStaticFields; i++) {
            StaticField staticField = readStaticField(l);
            staticFields.add(staticField);
        }

        nInstanceFields = l.readShort();
        instanceFields = new ArrayList<InstanceField>(nInstanceFields);

        for (int i = 0; i < nInstanceFields; i++) {
            InstanceField instanceField = readInstanceField(l);
            instanceFields.add(instanceField);
        }

        
    }

    private Constant readConstant(DataInputStreamWrapper l)
        throws IOException {
        short poolInd = l.readShort();
        byte typeByte = l.readByte();
        BasicTypeTag type = getByValue(typeByte);
        Number value;
        Character charValue;
        Boolean boolValue;
        IDField idValue;

        switch (type) {
            case CHAR:
                charValue = (Character) readEntryValue(l, type);
                return new Constant(poolInd, typeByte, charValue);

            case BOOLEAN:
                boolValue = (Boolean) readEntryValue(l, type);
                return new Constant(poolInd, typeByte, boolValue);

            case OBJECT:
                idValue = (IDField) readEntryValue(l, type);
                return new Constant(poolInd, typeByte, idValue);

            default:
                value = (Number) readEntryValue(l, type);
                return new Constant(poolInd, typeByte, value);
        }
    }

    private StaticField readStaticField(DataInputStreamWrapper l)
            throws IOException {

        IDField nameStringId = l.readId();
        byte typeByte = l.readByte();
        BasicTypeTag type = getByValue(typeByte);
        Number value;
        Character charValue;
        Boolean boolValue;
        IDField idValue;

        switch (type) {
            case CHAR:
                charValue = (Character) readEntryValue(l, type);
                return new StaticField(nameStringId, typeByte, charValue);

            case BOOLEAN:
                boolValue = (Boolean) readEntryValue(l, type);
                return new StaticField(nameStringId, typeByte, boolValue);

            case OBJECT:
                idValue = (IDField) readEntryValue(l, type);
                return new StaticField(nameStringId, typeByte, idValue);

            default:
                value = (Number) readEntryValue(l, type);
                return new StaticField(nameStringId, typeByte, value);
        }
    }

    private InstanceField readInstanceField(DataInputStreamWrapper l)
            throws IOException {

        IDField nameStringId = l.readId();
        byte typeByte = l.readByte();
        BasicTypeTag type = getByValue(typeByte);

        switch (type) {
            case CHAR:
                return new InstanceField(nameStringId, typeByte);

            case BOOLEAN:
                return new InstanceField(nameStringId, typeByte);

            default:
                return new InstanceField(nameStringId, typeByte);
        }
    }


    public static abstract class ClassDumpEntry implements Serializable {
        private BasicTypeTag typeOfEntry;

        private Number value;
        private Character charValue;
        private Boolean boolValue;
        private IDField idValue;

        public ClassDumpEntry() {}

        public ClassDumpEntry(byte typeOfEntry, Number value) {
            this.typeOfEntry = getByValue(typeOfEntry);
            this.value = value;
        }

        public ClassDumpEntry(byte typeOfEntry, Character value) {
            this.typeOfEntry = getByValue(typeOfEntry);
            this.charValue = value;
        }

        public ClassDumpEntry(byte typeOfEntry, Boolean value) {
            this.typeOfEntry = getByValue(typeOfEntry);
            this.boolValue = value;
        }

        public ClassDumpEntry(byte typeOfEntry, IDField value) {
            this.typeOfEntry = getByValue(typeOfEntry);
            this.idValue = value;
        }

        public BasicTypeTag getTypeOfEntry() {
            return typeOfEntry;
        }

        public Number getValue() {
            return value;
        }

        public Character getCharValue() {
            return charValue;
        }

        public Boolean getBoolValue() {
            return boolValue;
        }

        public IDField getIdValue() {
            return idValue;
        }
    }

    public static class Constant extends ClassDumpEntry{

        private short constPoolIndex;

        public Constant() {
            super();
        }

        public Constant(short constPoolIndex, byte typeOfEntry, Number value) {
            super(typeOfEntry, value);
            this.constPoolIndex = constPoolIndex;
        }

        public Constant(short constPoolIndex, byte typeOfEntry, Character value) {
            super(typeOfEntry, value);
            this.constPoolIndex = constPoolIndex;
        }

        public Constant(short constPoolIndex, byte typeOfEntry, Boolean value) {
            super(typeOfEntry, value);
            this.constPoolIndex = constPoolIndex;
        }
        public Constant(short constPoolIndex, byte typeOfEntry, IDField value) {
            super(typeOfEntry, value);
            this.constPoolIndex = constPoolIndex;
        }
    }

    public static class StaticField extends ClassDumpEntry{
        private IDField nameStringId;

        public StaticField() { super(); }

        public StaticField(IDField nameStringId, byte typeOfEntry, Number value) {
            super(typeOfEntry, value);
            this.nameStringId = nameStringId;
        }
        public StaticField(IDField nameStringId, byte typeOfEntry, Character charValue) {
            super(typeOfEntry, charValue);
            this.nameStringId = nameStringId;
        }
        public StaticField(IDField nameStringId, byte typeOfEntry, Boolean boolValue) {
            super(typeOfEntry, boolValue);
            this.nameStringId = nameStringId;
        }

        public StaticField(IDField nameStringId, byte typeOfEntry, IDField boolValue) {
            super(typeOfEntry, boolValue);
            this.nameStringId = nameStringId;
        }

        public IDField getNameStringId() {
            return nameStringId;
        }
    }

    public static class InstanceField extends ClassDumpEntry{
        private IDField nameStringId;

        public InstanceField() { super(); }

        public InstanceField(IDField nameStringId, BasicTypeTag typeTag) {
            this(nameStringId, typeTag.getCode());
        }


        public InstanceField(IDField nameStringId, byte typeOfEntry) {
            super(typeOfEntry, (Number) null);
            this.nameStringId = nameStringId;
        }

        public IDField getNameStringId() {
            return nameStringId;
        }

        /**
         * TODO : throw Exception instead?
         * @return null because instance field doesn't have a value without instance
         */
        public Number getValue() {
            return null;
        }
        /**
         * TODO : throw Exception instead?
         * @return null because instance field doesn't have a value without instance
         */
        public Character getCharValue() {
            return null;
        }
        /**
         * TODO : throw Exception instead?
         * @return null because instance field doesn't have a value without instance
         */
        public Boolean getBoolValue() {
            return null;
        }
        /**
         * TODO : throw Exception instead?
         * @return null because instance field doesn't have a value without instance
         */
        public IDField getIdValue() {
            return null;
        }
    }
}
