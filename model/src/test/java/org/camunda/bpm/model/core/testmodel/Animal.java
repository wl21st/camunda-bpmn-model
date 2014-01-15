/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.model.core.testmodel;

import org.camunda.bpm.model.core.ModelBuilder;
import org.camunda.bpm.model.core.impl.instance.ModelElementInstanceImpl;
import org.camunda.bpm.model.core.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.core.impl.type.reference.AttributeReferenceImpl;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.Attribute;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.core.type.SequenceBuilder;
import org.camunda.bpm.model.core.type.StringAttributeBuilder;
import org.camunda.bpm.model.core.type.reference.AttributeReference;
import org.camunda.bpm.model.core.type.reference.ElementReferenceCollection;

import java.util.Collection;

import static org.camunda.bpm.model.core.testmodel.TestModelConstants.*;

/**
 * @author Daniel Meyer
 *
 */
public abstract class Animal extends ModelElementInstanceImpl implements ModelElementInstance {

  static Attribute<String> idAttr;
  static Attribute<String> fatherAttr;
  static AttributeReference<Animal> fatherRef;
  static Attribute<String> motherAttr;
  static AttributeReference<Animal> motherRef;
  static ElementReferenceCollection<Animal, FriendRef> friendRefColl;
  static ElementReferenceCollection<Animal, PartnerRef> partnerRefColl;

  static void registerType(ModelBuilder modelBuilder) {

    ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(Animal.class, TYPE_NAME_ANIMAL)
      .namespaceUri(MODEL_NAMESPACE)
      .abstractType();

    idAttr = typeBuilder.stringAttribute(ATTRIBUTE_NAME_ID)
      .idAttribute()
      .build();

    StringAttributeBuilder fatherAttrBuilder = typeBuilder.stringAttribute(ATTRIBUTE_NAME_FATHER);
    fatherRef = fatherAttrBuilder.qNameAttributeReference(Animal.class)
      .build();
    fatherAttr = fatherAttrBuilder.build();

    StringAttributeBuilder motherAttrBuilder = typeBuilder.stringAttribute(ATTRIBUTE_NAME_MOTHER);
    motherRef = motherAttrBuilder.idAttributeReference(Animal.class)
      .build();
    motherAttr = motherAttrBuilder.build();

    SequenceBuilder sequence = typeBuilder.sequence();

    friendRefColl = sequence.elementCollection(FriendRef.class, ELEMENT_NAME_FRIEND_REF)
      .qNameElementReferenceCollection(Animal.class)
      .build();

    partnerRefColl = sequence.elementCollection(PartnerRef.class, ELEMENT_NAME_PARTNER_REF)
      .idElementReferenceCollection(Animal.class)
      .build();


    typeBuilder.build();
  }

  public Animal(ModelTypeInstanceContext instanceContext) {
    super(instanceContext);
  }

  public String getId() {
    return idAttr.getValue(this);
  }

  public void setId(String id) {
    idAttr.setValue(this, id);
  }

  public void setMother(Animal mother) {
    ((AttributeReferenceImpl<Animal>) motherRef).setReferencedElement(this, mother);
  }

  public Animal getMother() {
    return motherRef.getReferencedElement(this);
  }

  public void setFather(Animal father) {
    ((AttributeReferenceImpl<Animal>) fatherRef).setReferencedElement(this, father);
  }

  public Animal getFather() {
    return fatherRef.getReferencedElement(this);
  }

  public Collection<Animal> getFriendRefs() {
    return friendRefColl.getReferenceTargetElements(this);
  }

  public Collection<Animal> getPartnerRefs() {
    return partnerRefColl.getReferenceTargetElements(this);
  }
}
