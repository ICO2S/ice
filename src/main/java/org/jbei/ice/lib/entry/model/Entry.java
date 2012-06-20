package org.jbei.ice.lib.entry.model;

import com.google.common.base.Objects;
import org.hibernate.annotations.Cascade;
import org.jbei.ice.lib.dao.IModel;
import org.jbei.ice.lib.models.SelectionMarker;
import org.jbei.ice.lib.models.interfaces.IEntryValueObject;
import org.jbei.ice.lib.utils.JbeiConstants;
import org.jbei.ice.lib.utils.JbeirSettings;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Entry class is the most important class in gd-ice. Other record types extend this class.
 * <p/>
 * Entry class represent the unique handle for each record in the system. It provides the common
 * fields, such as the recordId (uuid), timestamps, owner and creator information, etc.
 * <p/>
 * Many of the fields accept mediawiki style linking tags. For example,
 * "[[jbei:JBx_000001|Descriptive Name]]" will automatically generate a clickable link to the part
 * JBx_000001 with text "Descriptive Name". The wiki link prefix (jbei:) in this case can be
 * confgured in the configuration file. In the future, links to other registries can be specified
 * via the configuration, similar to other mediawiki links.
 * <p/>
 * Description of Entry fields:
 * <p/>
 * <ul>
 * <li><b>id:</b> database id of an entry.</li>
 * <li><b>recordId:</b> 36 character globally unique identifier. Implemented as UUIDv4.</li>
 * <li><b>versionId:</b> 36 character globally unique identifier.</li>
 * <li><b>recordType:</b> The type of record. Currently there are plasmids, strains, arabidopsis
 * seeds, and parts. Parts represent linear DNA in a packaging format, such as Biobricks, or raw DNA
 * for ligationless assembly.</li>
 * <li><b>owner:</b> Owner is the person that has complete read and write access to a part. This
 * field is the user friendly string, such as their full name, and is not used by the system for
 * identification and association. See ownerEmail for that functionality. This field is also
 * distinct from the creator field.</li>
 * <li><b>ownerEmail:</b> Email address of the owner. Because an entry can be exchanged between
 * different registries, without having the corresponding account records be exchanged along with
 * it, association of entry with a user is done through this field, instead of the database id of
 * {@link org.jbei.ice.lib.account.model.Account}. This means that other classes (such as {@link org.jbei.ice.lib
 * .entry.sample.model.Sample}) also associate via the
 * email address. Consequently, email address must be unique to a gd-ice instance.</li>
 * <li><b>creator:</b> Creator is the person that has created this entry. If the entry came from
 * somewhere else, or was entered into this instance of gd-ice by someone other than the creator,
 * then the owner and the creator fields would be different. This field is the user friendly string,
 * such as their full name, and is not used by the system for identification and association.</li>
 * <li><b>creatorEmail:</b> Email address of the creator. For some very old entries, or entries that
 * came from a third party, email address maybe empty.</li>
 * <li><b>alias:</b> Comma separated list of alias names.</li>
 * <li><b>keywords:</b> Comma separated list of keywords.</li>
 * <li><b>status:</b> Status of this entry. Currently the options are complete, in progress, or
 * planned. This field in the future should be used to filter search results.</li>
 * <li><b>shortDescription:</b> A summary of the entry in a few words. This is what is displayed in
 * the search result summaries, and brevity is best.</li>
 * <li><b>longDescription:</b> Longer description for the entry. Details that are not part of the
 * summary description should be placed in this field. This field accepts markup text of different
 * styles. see longDescriptionType</li>
 * <li><b>longDescriptionType: Markup syntax used for long description. Currently plain text,
 * mediawiki, and confluence markup syntax is supported.</b>/
 * <li>
 * <li><b>references:</b> References for this entry.</li>
 * <li><b>creationTime:</b> Timestamp of creation of this entry.</li>
 * <li><b>modificationTime:</b> Timestamp of last modification of this entry.</li>
 * <li><b>bioSafetyLevel:</b> The biosafety level of this entry. In the future, this field will
 * propagate to other entries, so that a strain entry holding a higher level bioSafetyLevel plasmid
 * entry will inherit the higher bioSafetyLevel.</li>
 * <li><b>intellectualProperty:</b> Information about intellectual property (patent filing numbers,
 * etc) for this entry.</li>
 * <li><b>selectionMarkers:</b> {@link org.jbei.ice.lib.models.SelectionMarker}s for this entry. In the future,
 * this field
 * will propagate to other entries based on inheritence.</li>
 * <li><b>links:</b> URL or other links that point outside of this instance of gd-ice.</li>
 * <lli><b>names: </b> {@link Name}s for this entry.</li>
 * <li><b>partNumbers: </b> {@link PartNumber}s for this entry.</li>
 * <li><b>entryFundingSources</b> {@link EntryFundingSource}s for this entry.</li>
 * <li><b>parameters: {@link Parameter}s for this entry.</b></li>
 * </ul>
 *
 * @author Timothy Ham, Zinovii Dmytriv
 */
@Entity
@Table(name = "entries")
@SequenceGenerator(name = "sequence", sequenceName = "entries_id_seq", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class Entry implements IEntryValueObject, IModel {
    private static final long serialVersionUID = 1L;

    //TODO actually use these types

    /**
     * Mark up types for longDescription.
     *
     * @author Timothy Ham
     */
    public enum MarkupType {
        text, wiki, confluence
    }

    // TODO use these enums. Currently "in progress" with a space is used. 

    /**
     * Available status options.
     *
     * @author Timothy Ham
     */
    public enum StatusOptions {
        complete, in_progress, planned
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private long id;

    @Column(name = "record_id", length = 36, nullable = false, unique = true)
    private String recordId;

    @Column(name = "version_id", length = 36, nullable = false)
    private String versionId;

    @Column(name = "record_type", length = 127, nullable = false)
    private String recordType;

    @Column(name = "owner", length = 127)
    private String owner;

    @Column(name = "owner_email", length = 127)
    private String ownerEmail;

    @Column(name = "creator", length = 127)
    private String creator;

    @Column(name = "creator_email", length = 127)
    private String creatorEmail;

    @Column(name = "alias", length = 127)
    private String alias;

    @Column(name = "keywords", length = 127)
    private String keywords;

    @Column(name = "status", length = 127)
    private String status;

    @Column(name = "short_description")
    @Lob
    private String shortDescription;

    @Column(name = "long_description")
    @Lob
    private String longDescription;

    @Column(name = "long_description_type", length = 31, nullable = false)
    private String longDescriptionType;

    @Column(name = "literature_references")
    @Lob
    private String references;

    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(name = "modification_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationTime;

    @Column(name = "bio_safety_level")
    private Integer bioSafetyLevel;

    @Column(name = "intellectual_property")
    @Lob
    private String intellectualProperty;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "entry")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "entries_id")
    @OrderBy("id")
    private Set<SelectionMarker> selectionMarkers = new LinkedHashSet<SelectionMarker>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "entry")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "entries_id")
    @OrderBy("id")
    private final Set<Link> links = new LinkedHashSet<Link>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "entry")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "entries_id")
    @OrderBy("id")
    private final Set<Name> names = new LinkedHashSet<Name>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "entry")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "entries_id")
    @OrderBy("id")
    private final Set<PartNumber> partNumbers = new LinkedHashSet<PartNumber>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "entry")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "entries_id")
    @OrderBy("id")
    private final Set<EntryFundingSource> entryFundingSources = new LinkedHashSet<EntryFundingSource>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "entry")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "entries_id")
    @OrderBy("id")
    private final List<Parameter> parameters = new ArrayList<Parameter>();

    public Entry() {
    }

    public Entry(String recordId, String versionId, String recordType, String owner,
            String ownerEmail, String creator, String creatorEmail, String status, String alias,
            String keywords, String shortDescription, String longDescription,
            String longDescriptionType, String references, Date creationTime, Date modificationTime) {
        this.recordId = recordId;
        this.versionId = versionId;
        this.recordType = recordType;
        this.owner = owner;
        this.ownerEmail = ownerEmail;
        this.creator = creator;
        this.creatorEmail = creatorEmail;
        this.status = status;
        this.alias = alias;
        this.keywords = keywords;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.longDescriptionType = longDescriptionType;
        this.references = references;
        this.creationTime = creationTime;
        this.modificationTime = modificationTime;
    }

    @Override
    @XmlTransient
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getRecordId() {
        return recordId;
    }

    @Override
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Override
    @XmlTransient
    public String getVersionId() {
        return versionId;
    }

    @Override
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    @Override
    @XmlTransient
    public String getRecordType() {
        return recordType;
    }

    @Override
    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    @Override
    public Set<Name> getNames() {
        return names;
    }

    @Override
    public void setNames(Set<Name> inputNames) {
        /*
         * Warning! This is a hibernate workaround. 
         */

        // for JAXB webservices should be this way
        if (inputNames == null) {
            names.clear();

            return;
        }

        if (inputNames != names) {
            names.clear();
            names.addAll(inputNames);
        }
    }

    public Name getOneName() {
        Name result = null;
        if (names.size() > 0) {
            result = (Name) names.toArray()[0];
        }
        return result;
    }

    public String getNamesAsString() {
        String result = "";
        ArrayList<String> names = new ArrayList<String>();
        for (Name name : this.names) {
            names.add(name.getName());
        }
        result = org.jbei.ice.lib.utils.Utils.join(", ", names);

        return result;
    }

    @Override
    public Set<PartNumber> getPartNumbers() {
        return partNumbers;
    }

    /**
     * Generate the comma separated string representation of {@link PartNumber}s associated with
     * this entry.
     *
     * @return Comma separated part numbers.
     */
    public String getPartNumbersAsString() {
        String result = "";
        ArrayList<String> numbers = new ArrayList<String>();
        for (PartNumber number : partNumbers) {
            numbers.add(number.getPartNumber());
        }
        result = org.jbei.ice.lib.utils.Utils.join(", ", numbers);

        return result;
    }

    /**
     * Return the first {@link PartNumber} associated with this entry, preferring the PartNumber
     * local to this instance of gd-ice.
     *
     * @return PartNumber.
     */
    public PartNumber getOnePartNumber() {
        PartNumber result = null;
        // prefer local part number prefix over other prefixes
        if (partNumbers.size() > 0) {
            for (PartNumber partNumber : partNumbers) {
                if (partNumber.getPartNumber().contains(
                        JbeirSettings.getSetting("PART_NUMBER_PREFIX"))) {
                    result = partNumber;
                }
            }
            if (result == null) {
                result = (PartNumber) partNumbers.toArray()[0];
            }
        }
        return result;
    }

    @Override
    public void setPartNumbers(Set<PartNumber> inputPartNumbers) {
        // for JAXB webservices should be this way
        if (inputPartNumbers == null) {
            partNumbers.clear();

            return;
        }

        if (inputPartNumbers != partNumbers) {
            partNumbers.clear();
            partNumbers.addAll(inputPartNumbers);
        }
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String getOwnerEmail() {
        return ownerEmail;
    }

    @Override
    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    @Override
    public String getCreator() {
        return creator;
    }

    @Override
    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String getCreatorEmail() {
        return creatorEmail;
    }

    @Override
    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public Set<SelectionMarker> getSelectionMarkers() {
        return selectionMarkers;
    }

    /**
     * Generate a String representation of the {@link SelectionMarker}s associated with this entry.
     *
     * @return Comma separated selection markers.
     */
    public String getSelectionMarkersAsString() {
        String result = "";
        ArrayList<String> markers = new ArrayList<String>();
        for (SelectionMarker marker : selectionMarkers) {
            markers.add(marker.getName());
        }
        result = org.jbei.ice.lib.utils.Utils.join(", ", markers);

        return result;
    }

    @Override
    public void setSelectionMarkers(Set<SelectionMarker> inputSelectionMarkers) {
        if (inputSelectionMarkers == null) {
            selectionMarkers.clear();

            return;
        }

        if (inputSelectionMarkers == selectionMarkers) {
            selectionMarkers = inputSelectionMarkers;
        } else {
            selectionMarkers.clear();
            selectionMarkers.addAll(inputSelectionMarkers);
        }
    }

    @Override
    public Set<Link> getLinks() {
        return links;
    }

    /**
     * String representation of {@link Link}s.
     *
     * @return Comma separated list of links.
     */
    public String getLinksAsString() {
        String result = "";
        ArrayList<String> links = new ArrayList<String>();
        for (Link link : this.links) {
            links.add(link.getLink());
        }
        result = org.jbei.ice.lib.utils.Utils.join(", ", links);

        return result;
    }

    @Override
    public void setLinks(Set<Link> inputLinks) {
        if (inputLinks == null) {
            links.clear();

            return;
        }

        if (inputLinks != links) {
            links.clear();
            links.addAll(inputLinks);
        }
    }

    @Override
    public String getKeywords() {
        return keywords;
    }

    @Override
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public String getLongDescription() {
        return longDescription;
    }

    @Override
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getLongDescriptionType() {
        return longDescriptionType;
    }

    public void setLongDescriptionType(String longDescriptionType) {
        this.longDescriptionType = longDescriptionType;
    }

    @Override
    public String getReferences() {
        return references;
    }

    @Override
    public void setReferences(String references) {
        this.references = references;
    }

    @Override
    @XmlTransient
    public Date getCreationTime() {
        return creationTime;
    }

    @Override
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    @XmlTransient
    public Date getModificationTime() {
        return modificationTime;
    }

    @Override
    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    public void setBioSafetyLevel(Integer bioSafetyLevel) {
        this.bioSafetyLevel = bioSafetyLevel;
    }

    public Integer getBioSafetyLevel() {
        return bioSafetyLevel;
    }

    public void setIntellectualProperty(String intellectualProperty) {
        this.intellectualProperty = intellectualProperty;
    }

    public String getIntellectualProperty() {
        return intellectualProperty;
    }

    public void setEntryFundingSources(Set<EntryFundingSource> inputEntryFundingSources) {
        if (inputEntryFundingSources == null) {
            entryFundingSources.clear();

            return;
        }

        if (inputEntryFundingSources != entryFundingSources) {
            entryFundingSources.clear();
            entryFundingSources.addAll(inputEntryFundingSources);
        }
    }

    public Set<EntryFundingSource> getEntryFundingSources() {
        return entryFundingSources;
    }

    public void setParameters(List<Parameter> inputParameters) {
        if (inputParameters == null) {
            parameters.clear();
            return;
        }
        if (inputParameters != parameters) {
            for (Parameter parameter : inputParameters) {
                parameter.setEntry(this);
            }
            parameters.clear();
            parameters.addAll(inputParameters);
        }
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Get the principalInvestigator field of the entry's EntryFundingSource.
     *
     * @return principalInvestigator field as String.
     */
    public String principalInvestigatorToString() {
        String principalInvestigator = "";

        for (EntryFundingSource entryFundingSource : entryFundingSources) {
            principalInvestigator = entryFundingSource.getFundingSource()
                                                      .getPrincipalInvestigator();
        }

        if (principalInvestigator == null) {
            principalInvestigator = "";
        }

        return principalInvestigator;
    }

    /**
     * Get the funding source field of the entry's EntryFundingSource.
     *
     * @return funding source field as String.
     */
    public String fundingSourceToString() {
        String fundingSource = "";

        for (EntryFundingSource entryFundingSource : entryFundingSources) {
            fundingSource = entryFundingSource.getFundingSource().getFundingSource();
        }

        if (fundingSource == null) {
            fundingSource = "";
        }

        return fundingSource;
    }

    /**
     * Generate the options map of the bioSafetyLevel field containing friendly names for the
     * fields.
     *
     * @return Map of biosafety levels and names.
     */
    public static Map<String, String> getBioSafetyLevelOptionsMap() {
        Map<String, String> resultMap = new LinkedHashMap<String, String>();

        resultMap.put("1", "Level 1");
        resultMap.put("2", "Level 2");

        return resultMap;
    }

    /**
     * Generate the options map of status options containing friendly names for status field.
     *
     * @return Map of options and names.
     */
    public static Map<String, String> getStatusOptionsMap() {
        Map<String, String> resultMap = new LinkedHashMap<String, String>();

        resultMap.put("complete", JbeiConstants.getStatus("complete"));
        resultMap.put("in progress", JbeiConstants.getStatus("in progress"));
        resultMap.put("planned", JbeiConstants.getStatus("planned"));

        return resultMap;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getRecordId());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        final Entry other = (Entry) obj;

        return Objects.equal(this.recordId, other.getRecordId())
                && Objects.equal(this.recordType, other.getRecordType())
                && Objects.equal(this.getId(), other.getId());
    }
}