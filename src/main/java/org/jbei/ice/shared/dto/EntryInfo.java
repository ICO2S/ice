package org.jbei.ice.shared.dto;

import java.util.ArrayList;
import java.util.Date;

import org.jbei.ice.client.entry.view.model.SampleStorage;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EntryInfo implements IsSerializable {

    public enum EntryType implements IsSerializable {
        STRAIN("Strain", "strain"), PLASMID("Plasmid", "plasmid"), PART("Part", "part"), ARABIDOPSIS(
                "Arabidopsis", "arabidopsis");

        private String name;
        private String display;

        EntryType() {
        }

        EntryType(String display, String name) {
            this.display = display;
            this.name = name;
        }

        public static EntryType nameToType(String name) {
            for (EntryType type : EntryType.values()) {
                if (name.equalsIgnoreCase(type.getName()))
                    return type;
            }

            return null;
        }

        public String getName() {
            return this.name;
        }

        public String getDisplay() {
            return this.display;
        }

        @Override
        public String toString() {
            return this.display;
        }
    }

    private long id;
    private String recordId;
    private String versionId;
    private String name;
    private EntryType type;
    private String owner;
    private String ownerEmail;
    private String creator;
    private String creatorEmail;
    private String alias;
    private String keywords;
    private String status;
    private String shortDescription;
    private String linkifiedShortDescription;
    private String longDescription;
    private String parsedDescription;
    private String longDescriptionType;
    private String references;
    private Date creationTime;
    private Date modificationTime;
    private Integer bioSafetyLevel;
    private String intellectualProperty;
    private String partId;
    private String links; // comma separated list of links
    private String linkifiedLinks;
    private String principalInvestigator;
    private String selectionMarkers;
    private String fundingSource;
    private boolean hasAttachment;
    private boolean hasSample;
    private boolean hasSequence;
    private ArrayList<AttachmentInfo> attachments; // TODO : create another object that HAS A EntryInfo and contains these as well
    private ArrayList<SampleStorage> sampleStorage;
    private ArrayList<SequenceAnalysisInfo> sequenceAnalysis;
    private ArrayList<ParameterInfo> parameters;
    private boolean canEdit; // whether current user that requested this entry info has write privs

    public EntryInfo() {

    }

    public EntryInfo(EntryType type) {
        this.type = type;
        sampleStorage = new ArrayList<SampleStorage>();
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public EntryType getType() {
        return type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getLongDescriptionType() {
        return longDescriptionType;
    }

    public void setLongDescriptionType(String longDescriptionType) {
        this.longDescriptionType = longDescriptionType;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getPrincipalInvestigator() {
        return principalInvestigator;
    }

    public void setPrincipalInvestigator(String principalInvestigator) {
        this.principalInvestigator = principalInvestigator;
    }

    public void setSelectionMarkers(String markers) {
        this.selectionMarkers = markers;
    }

    public String getSelectionMarkers() {
        return this.selectionMarkers;
    }

    public ArrayList<AttachmentInfo> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(ArrayList<AttachmentInfo> attachments) {
        this.attachments = attachments;
    }

    public ArrayList<SequenceAnalysisInfo> getSequenceAnalysis() {
        return this.sequenceAnalysis;
    }

    public void setSequenceAnalysis(ArrayList<SequenceAnalysisInfo> analyses) {
        this.sequenceAnalysis = analyses;
    }

    public ArrayList<ParameterInfo> getParameters() {
        return this.parameters;
    }

    public void setParameters(ArrayList<ParameterInfo> parameters) {
        this.parameters = parameters;
    }

    public String getFundingSource() {
        return this.fundingSource;
    }

    public void setFundingSource(String fundingSource) {
        this.fundingSource = fundingSource;
    }

    public String getLinks() {
        return this.links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public ArrayList<SampleStorage> getSampleStorage() {
        return sampleStorage;
    }

    public void setSampleMap(ArrayList<SampleStorage> sampleStorage) {
        this.sampleStorage.clear();
        this.sampleStorage.addAll(sampleStorage);
    }

    public boolean isHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }

    public boolean isHasSample() {
        return hasSample;
    }

    public void setHasSample(boolean hasSample) {
        this.hasSample = hasSample;
    }

    public boolean isHasSequence() {
        return hasSequence;
    }

    public void setHasSequence(boolean hasSequence) {
        this.hasSequence = hasSequence;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getParsedDescription() {
        return parsedDescription;
    }

    public void setParsedDescription(String parsedDescription) {
        this.parsedDescription = parsedDescription;
    }

    public String getLinkifiedLinks() {
        return linkifiedLinks;
    }

    public void setLinkifiedLinks(String linkifiedLinks) {
        this.linkifiedLinks = linkifiedLinks;
    }

    public String getLinkifiedShortDescription() {
        return linkifiedShortDescription;
    }

    public void setLinkifiedShortDescription(String linkifiedShortDescription) {
        this.linkifiedShortDescription = linkifiedShortDescription;
    }
}