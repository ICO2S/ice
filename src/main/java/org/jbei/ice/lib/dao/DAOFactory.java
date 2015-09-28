package org.jbei.ice.lib.dao;

import org.jbei.ice.lib.dao.hibernate.*;

/**
 * @author Hector Plahar
 */
public class DAOFactory {

    private static AccountDAO accountDAO;
    private static AccountPreferencesDAO accountPreferencesDAO;
    private static AttachmentDAO attachmentDAO;
    private static BulkUploadDAO bulkUploadDAO;
    private static CommentDAO commentDAO;
    private static ConfigurationDAO configurationDAO;
    private static EntryDAO entryDAO;
    private static FolderDAO folderDAO;
    private static GroupDAO groupDAO;
    private static MessageDAO messageDAO;
    private static PermissionDAO permissionDAO;
    private static PreferencesDAO preferencesDAO;
    private static RemotePartnerDAO remotePartnerDAO;
    private static RequestDAO requestDAO;
    private static SampleDAO sampleDAO;
    private static SequenceDAO sequenceDAO;
    private static StorageDAO storageDAO;
    private static TraceSequenceDAO traceSequenceDAO;
    private static ShotgunSequenceDAO shotgunSequenceDAO;
    private static AuditDAO auditDAO;
    private static RemotePermissionDAO remotePermissionDAO;
    private static ExperimentDAO experimentDAO;
    private static ParameterDAO parameterDAO;

    public static AccountDAO getAccountDAO() {
        if (accountDAO == null)
            accountDAO = new AccountDAO();
        return accountDAO;
    }

    public static AccountPreferencesDAO getAccountPreferencesDAO() {
        if (accountPreferencesDAO == null)
            accountPreferencesDAO = new AccountPreferencesDAO();
        return accountPreferencesDAO;
    }

    public static AttachmentDAO getAttachmentDAO() {
        if (attachmentDAO == null)
            attachmentDAO = new AttachmentDAO();
        return attachmentDAO;
    }

    public static AuditDAO getAuditDAO() {
        if (auditDAO == null)
            auditDAO = new AuditDAO();
        return auditDAO;
    }

    public static BulkUploadDAO getBulkUploadDAO() {
        if (bulkUploadDAO == null)
            bulkUploadDAO = new BulkUploadDAO();
        return bulkUploadDAO;
    }

    public static CommentDAO getCommentDAO() {
        if (commentDAO == null)
            commentDAO = new CommentDAO();
        return commentDAO;
    }

    public static ConfigurationDAO getConfigurationDAO() {
        if (configurationDAO == null)
            configurationDAO = new ConfigurationDAO();
        return configurationDAO;
    }

    public static EntryDAO getEntryDAO() {
        if (entryDAO == null)
            entryDAO = new EntryDAO();
        return entryDAO;
    }

    public static FolderDAO getFolderDAO() {
        if (folderDAO == null)
            folderDAO = new FolderDAO();
        return folderDAO;
    }

    public static GroupDAO getGroupDAO() {
        if (groupDAO == null)
            groupDAO = new GroupDAO();
        return groupDAO;
    }

    public static MessageDAO getMessageDAO() {
        if (messageDAO == null)
            messageDAO = new MessageDAO();
        return messageDAO;
    }

    public static PermissionDAO getPermissionDAO() {
        if (permissionDAO == null)
            permissionDAO = new PermissionDAO();
        return permissionDAO;
    }

    public static PreferencesDAO getPreferencesDAO() {
        if (preferencesDAO == null)
            preferencesDAO = new PreferencesDAO();
        return preferencesDAO;
    }

    public static RemotePartnerDAO getRemotePartnerDAO() {
        if (remotePartnerDAO == null)
            remotePartnerDAO = new RemotePartnerDAO();
        return remotePartnerDAO;
    }

    public static RequestDAO getRequestDAO() {
        if (requestDAO == null)
            requestDAO = new RequestDAO();
        return requestDAO;
    }

    public static SampleDAO getSampleDAO() {
        if (sampleDAO == null)
            sampleDAO = new SampleDAO();
        return sampleDAO;
    }

    public static SequenceDAO getSequenceDAO() {
        if (sequenceDAO == null)
            sequenceDAO = new SequenceDAO();
        return sequenceDAO;
    }

    public static StorageDAO getStorageDAO() {
        if (storageDAO == null)
            storageDAO = new StorageDAO();
        return storageDAO;
    }

    public static TraceSequenceDAO getTraceSequenceDAO() {
        if (traceSequenceDAO == null)
            traceSequenceDAO = new TraceSequenceDAO();
        return traceSequenceDAO;
    }


    public static ShotgunSequenceDAO getShotgunSequenceDAO() {
        if (shotgunSequenceDAO == null)
            shotgunSequenceDAO = new ShotgunSequenceDAO();
        return shotgunSequenceDAO;
    }

    public static RemotePermissionDAO getRemotePermissionDAO() {
        if (remotePermissionDAO == null)
            remotePermissionDAO = new RemotePermissionDAO();
        return remotePermissionDAO;
    }

    public static ExperimentDAO getExperimentDAO() {
        if (experimentDAO == null)
            experimentDAO = new ExperimentDAO();
        return experimentDAO;
    }

    public static ParameterDAO getParameterDAO() {
        if (parameterDAO == null)
            parameterDAO = new ParameterDAO();
        return parameterDAO;
    }
}
