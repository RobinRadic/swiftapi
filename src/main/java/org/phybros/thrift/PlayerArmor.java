/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.phybros.thrift;

import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

/**
 * Represents the armor that the player is wearing
 */
public class PlayerArmor implements org.apache.thrift.TBase<PlayerArmor, PlayerArmor._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PlayerArmor");

  private static final org.apache.thrift.protocol.TField HELMET_FIELD_DESC = new org.apache.thrift.protocol.TField("helmet", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField CHESTPLATE_FIELD_DESC = new org.apache.thrift.protocol.TField("chestplate", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField LEGGINGS_FIELD_DESC = new org.apache.thrift.protocol.TField("leggings", org.apache.thrift.protocol.TType.STRUCT, (short)3);
  private static final org.apache.thrift.protocol.TField BOOTS_FIELD_DESC = new org.apache.thrift.protocol.TField("boots", org.apache.thrift.protocol.TType.STRUCT, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PlayerArmorStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PlayerArmorTupleSchemeFactory());
  }

  /**
   * The item in the player's helmet armor slot
   */
  public ItemStack helmet; // required
  /**
   * The item in the player's chestplate armor slot
   */
  public ItemStack chestplate; // required
  /**
   * The item in the player's leggings armor slot
   */
  public ItemStack leggings; // required
  /**
   * The item in the player's boots armor slot
   */
  public ItemStack boots; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * The item in the player's helmet armor slot
     */
    HELMET((short)1, "helmet"),
    /**
     * The item in the player's chestplate armor slot
     */
    CHESTPLATE((short)2, "chestplate"),
    /**
     * The item in the player's leggings armor slot
     */
    LEGGINGS((short)3, "leggings"),
    /**
     * The item in the player's boots armor slot
     */
    BOOTS((short)4, "boots");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // HELMET
          return HELMET;
        case 2: // CHESTPLATE
          return CHESTPLATE;
        case 3: // LEGGINGS
          return LEGGINGS;
        case 4: // BOOTS
          return BOOTS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.HELMET, new org.apache.thrift.meta_data.FieldMetaData("helmet", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ItemStack.class)));
    tmpMap.put(_Fields.CHESTPLATE, new org.apache.thrift.meta_data.FieldMetaData("chestplate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ItemStack.class)));
    tmpMap.put(_Fields.LEGGINGS, new org.apache.thrift.meta_data.FieldMetaData("leggings", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ItemStack.class)));
    tmpMap.put(_Fields.BOOTS, new org.apache.thrift.meta_data.FieldMetaData("boots", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ItemStack.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PlayerArmor.class, metaDataMap);
  }

  public PlayerArmor() {
  }

  public PlayerArmor(
    ItemStack helmet,
    ItemStack chestplate,
    ItemStack leggings,
    ItemStack boots)
  {
    this();
    this.helmet = helmet;
    this.chestplate = chestplate;
    this.leggings = leggings;
    this.boots = boots;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PlayerArmor(PlayerArmor other) {
    if (other.isSetHelmet()) {
      this.helmet = new ItemStack(other.helmet);
    }
    if (other.isSetChestplate()) {
      this.chestplate = new ItemStack(other.chestplate);
    }
    if (other.isSetLeggings()) {
      this.leggings = new ItemStack(other.leggings);
    }
    if (other.isSetBoots()) {
      this.boots = new ItemStack(other.boots);
    }
  }

  public PlayerArmor deepCopy() {
    return new PlayerArmor(this);
  }

  @Override
  public void clear() {
    this.helmet = null;
    this.chestplate = null;
    this.leggings = null;
    this.boots = null;
  }

  /**
   * The item in the player's helmet armor slot
   */
  public ItemStack getHelmet() {
    return this.helmet;
  }

  /**
   * The item in the player's helmet armor slot
   */
  public PlayerArmor setHelmet(ItemStack helmet) {
    this.helmet = helmet;
    return this;
  }

  public void unsetHelmet() {
    this.helmet = null;
  }

  /** Returns true if field helmet is set (has been assigned a value) and false otherwise */
  public boolean isSetHelmet() {
    return this.helmet != null;
  }

  public void setHelmetIsSet(boolean value) {
    if (!value) {
      this.helmet = null;
    }
  }

  /**
   * The item in the player's chestplate armor slot
   */
  public ItemStack getChestplate() {
    return this.chestplate;
  }

  /**
   * The item in the player's chestplate armor slot
   */
  public PlayerArmor setChestplate(ItemStack chestplate) {
    this.chestplate = chestplate;
    return this;
  }

  public void unsetChestplate() {
    this.chestplate = null;
  }

  /** Returns true if field chestplate is set (has been assigned a value) and false otherwise */
  public boolean isSetChestplate() {
    return this.chestplate != null;
  }

  public void setChestplateIsSet(boolean value) {
    if (!value) {
      this.chestplate = null;
    }
  }

  /**
   * The item in the player's leggings armor slot
   */
  public ItemStack getLeggings() {
    return this.leggings;
  }

  /**
   * The item in the player's leggings armor slot
   */
  public PlayerArmor setLeggings(ItemStack leggings) {
    this.leggings = leggings;
    return this;
  }

  public void unsetLeggings() {
    this.leggings = null;
  }

  /** Returns true if field leggings is set (has been assigned a value) and false otherwise */
  public boolean isSetLeggings() {
    return this.leggings != null;
  }

  public void setLeggingsIsSet(boolean value) {
    if (!value) {
      this.leggings = null;
    }
  }

  /**
   * The item in the player's boots armor slot
   */
  public ItemStack getBoots() {
    return this.boots;
  }

  /**
   * The item in the player's boots armor slot
   */
  public PlayerArmor setBoots(ItemStack boots) {
    this.boots = boots;
    return this;
  }

  public void unsetBoots() {
    this.boots = null;
  }

  /** Returns true if field boots is set (has been assigned a value) and false otherwise */
  public boolean isSetBoots() {
    return this.boots != null;
  }

  public void setBootsIsSet(boolean value) {
    if (!value) {
      this.boots = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case HELMET:
      if (value == null) {
        unsetHelmet();
      } else {
        setHelmet((ItemStack)value);
      }
      break;

    case CHESTPLATE:
      if (value == null) {
        unsetChestplate();
      } else {
        setChestplate((ItemStack)value);
      }
      break;

    case LEGGINGS:
      if (value == null) {
        unsetLeggings();
      } else {
        setLeggings((ItemStack)value);
      }
      break;

    case BOOTS:
      if (value == null) {
        unsetBoots();
      } else {
        setBoots((ItemStack)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case HELMET:
      return getHelmet();

    case CHESTPLATE:
      return getChestplate();

    case LEGGINGS:
      return getLeggings();

    case BOOTS:
      return getBoots();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case HELMET:
      return isSetHelmet();
    case CHESTPLATE:
      return isSetChestplate();
    case LEGGINGS:
      return isSetLeggings();
    case BOOTS:
      return isSetBoots();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PlayerArmor)
      return this.equals((PlayerArmor)that);
    return false;
  }

  public boolean equals(PlayerArmor that) {
    if (that == null)
      return false;

    boolean this_present_helmet = true && this.isSetHelmet();
    boolean that_present_helmet = true && that.isSetHelmet();
    if (this_present_helmet || that_present_helmet) {
      if (!(this_present_helmet && that_present_helmet))
        return false;
      if (!this.helmet.equals(that.helmet))
        return false;
    }

    boolean this_present_chestplate = true && this.isSetChestplate();
    boolean that_present_chestplate = true && that.isSetChestplate();
    if (this_present_chestplate || that_present_chestplate) {
      if (!(this_present_chestplate && that_present_chestplate))
        return false;
      if (!this.chestplate.equals(that.chestplate))
        return false;
    }

    boolean this_present_leggings = true && this.isSetLeggings();
    boolean that_present_leggings = true && that.isSetLeggings();
    if (this_present_leggings || that_present_leggings) {
      if (!(this_present_leggings && that_present_leggings))
        return false;
      if (!this.leggings.equals(that.leggings))
        return false;
    }

    boolean this_present_boots = true && this.isSetBoots();
    boolean that_present_boots = true && that.isSetBoots();
    if (this_present_boots || that_present_boots) {
      if (!(this_present_boots && that_present_boots))
        return false;
      if (!this.boots.equals(that.boots))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(PlayerArmor other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    PlayerArmor typedOther = (PlayerArmor)other;

    lastComparison = Boolean.valueOf(isSetHelmet()).compareTo(typedOther.isSetHelmet());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHelmet()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.helmet, typedOther.helmet);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetChestplate()).compareTo(typedOther.isSetChestplate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChestplate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.chestplate, typedOther.chestplate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLeggings()).compareTo(typedOther.isSetLeggings());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLeggings()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.leggings, typedOther.leggings);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBoots()).compareTo(typedOther.isSetBoots());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBoots()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.boots, typedOther.boots);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("PlayerArmor(");
    boolean first = true;

    sb.append("helmet:");
    if (this.helmet == null) {
      sb.append("null");
    } else {
      sb.append(this.helmet);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("chestplate:");
    if (this.chestplate == null) {
      sb.append("null");
    } else {
      sb.append(this.chestplate);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("leggings:");
    if (this.leggings == null) {
      sb.append("null");
    } else {
      sb.append(this.leggings);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("boots:");
    if (this.boots == null) {
      sb.append("null");
    } else {
      sb.append(this.boots);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (helmet != null) {
      helmet.validate();
    }
    if (chestplate != null) {
      chestplate.validate();
    }
    if (leggings != null) {
      leggings.validate();
    }
    if (boots != null) {
      boots.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PlayerArmorStandardSchemeFactory implements SchemeFactory {
    public PlayerArmorStandardScheme getScheme() {
      return new PlayerArmorStandardScheme();
    }
  }

  private static class PlayerArmorStandardScheme extends StandardScheme<PlayerArmor> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PlayerArmor struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // HELMET
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.helmet = new ItemStack();
              struct.helmet.read(iprot);
              struct.setHelmetIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CHESTPLATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.chestplate = new ItemStack();
              struct.chestplate.read(iprot);
              struct.setChestplateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LEGGINGS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.leggings = new ItemStack();
              struct.leggings.read(iprot);
              struct.setLeggingsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // BOOTS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.boots = new ItemStack();
              struct.boots.read(iprot);
              struct.setBootsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, PlayerArmor struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.helmet != null) {
        oprot.writeFieldBegin(HELMET_FIELD_DESC);
        struct.helmet.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.chestplate != null) {
        oprot.writeFieldBegin(CHESTPLATE_FIELD_DESC);
        struct.chestplate.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.leggings != null) {
        oprot.writeFieldBegin(LEGGINGS_FIELD_DESC);
        struct.leggings.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.boots != null) {
        oprot.writeFieldBegin(BOOTS_FIELD_DESC);
        struct.boots.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PlayerArmorTupleSchemeFactory implements SchemeFactory {
    public PlayerArmorTupleScheme getScheme() {
      return new PlayerArmorTupleScheme();
    }
  }

  private static class PlayerArmorTupleScheme extends TupleScheme<PlayerArmor> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PlayerArmor struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetHelmet()) {
        optionals.set(0);
      }
      if (struct.isSetChestplate()) {
        optionals.set(1);
      }
      if (struct.isSetLeggings()) {
        optionals.set(2);
      }
      if (struct.isSetBoots()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetHelmet()) {
        struct.helmet.write(oprot);
      }
      if (struct.isSetChestplate()) {
        struct.chestplate.write(oprot);
      }
      if (struct.isSetLeggings()) {
        struct.leggings.write(oprot);
      }
      if (struct.isSetBoots()) {
        struct.boots.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PlayerArmor struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.helmet = new ItemStack();
        struct.helmet.read(iprot);
        struct.setHelmetIsSet(true);
      }
      if (incoming.get(1)) {
        struct.chestplate = new ItemStack();
        struct.chestplate.read(iprot);
        struct.setChestplateIsSet(true);
      }
      if (incoming.get(2)) {
        struct.leggings = new ItemStack();
        struct.leggings.read(iprot);
        struct.setLeggingsIsSet(true);
      }
      if (incoming.get(3)) {
        struct.boots = new ItemStack();
        struct.boots.read(iprot);
        struct.setBootsIsSet(true);
      }
    }
  }

}

