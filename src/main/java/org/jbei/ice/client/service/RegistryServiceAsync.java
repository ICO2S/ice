package org.jbei.ice.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbei.ice.client.entry.display.model.SampleStorage;
import org.jbei.ice.client.exception.AuthenticationException;
import org.jbei.ice.lib.shared.ColumnField;
import org.jbei.ice.lib.shared.EntryAddType;
import org.jbei.ice.lib.shared.ExportAsOption;
import org.jbei.ice.lib.shared.dto.AccountResults;
import org.jbei.ice.lib.shared.dto.ConfigurationKey;
import org.jbei.ice.lib.shared.dto.NewsItem;
import org.jbei.ice.lib.shared.dto.PartSample;
import org.jbei.ice.lib.shared.dto.bulkupload.BulkUploadAutoUpdate;
import org.jbei.ice.lib.shared.dto.bulkupload.BulkUploadInfo;
import org.jbei.ice.lib.shared.dto.bulkupload.PreferenceInfo;
import org.jbei.ice.lib.shared.dto.comment.UserComment;
import org.jbei.ice.lib.shared.dto.entry.AutoCompleteField;
import org.jbei.ice.lib.shared.dto.entry.EntryType;
import org.jbei.ice.lib.shared.dto.entry.PartData;
import org.jbei.ice.lib.shared.dto.entry.SequenceAnalysisInfo;
import org.jbei.ice.lib.shared.dto.folder.FolderDetails;
import org.jbei.ice.lib.shared.dto.group.GroupType;
import org.jbei.ice.lib.shared.dto.group.UserGroup;
import org.jbei.ice.lib.shared.dto.message.MessageInfo;
import org.jbei.ice.lib.shared.dto.message.MessageList;
import org.jbei.ice.lib.shared.dto.permission.AccessPermission;
import org.jbei.ice.lib.shared.dto.sample.SampleRequest;
import org.jbei.ice.lib.shared.dto.sample.SampleRequestStatus;
import org.jbei.ice.lib.shared.dto.sample.SampleRequestType;
import org.jbei.ice.lib.shared.dto.search.IndexType;
import org.jbei.ice.lib.shared.dto.search.SearchQuery;
import org.jbei.ice.lib.shared.dto.search.SearchResults;
import org.jbei.ice.lib.shared.dto.user.PreferenceKey;
import org.jbei.ice.lib.shared.dto.user.User;
import org.jbei.ice.lib.shared.dto.web.RegistryPartner;
import org.jbei.ice.lib.shared.dto.web.WebOfRegistries;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

/**
 * Part of the GWT RPC interface definition.
 * Asynchronous interface to the ICE Registry service, to be called by the client
 *
 * @author Hector Plahar
 */
public interface RegistryServiceAsync {

    void login(String name, String pass, AsyncCallback<User> callback);

    void sessionValid(String sid, AsyncCallback<User> callback);

    void logout(String sessionId, AsyncCallback<Boolean> callback);

    void retrieveEntryDetails(String sessionId, long id, String url, AsyncCallback<PartData> callback)
            throws AuthenticationException;

    void retrieveEntryTipDetails(String sessionId, long id, String url, AsyncCallback<PartData> callback)
            throws AuthenticationException;

    void getPermissionSuggestions(String sid, SuggestOracle.Request req, AsyncCallback<SuggestOracle.Response> callback)
            throws AuthenticationException;

    void retrieveEntriesForFolder(String sessionId, long folderId, ColumnField sort, boolean asc,
            int start, int limit, AsyncCallback<FolderDetails> callback);

    void retrieveUserEntries(String sid, String userId, ColumnField sort, boolean asc,
            int start, int limit, AsyncCallback<FolderDetails> asyncCallback);

    void performSearch(String sid, SearchQuery searchQuery, boolean isWeb,
            AsyncCallback<SearchResults> callback) throws AuthenticationException;

    void retrieveUserSavedDrafts(String sid, AsyncCallback<ArrayList<BulkUploadInfo>> callback)
            throws AuthenticationException;

    void retrieveDraftsPendingVerification(String sid, AsyncCallback<ArrayList<BulkUploadInfo>> callback)
            throws AuthenticationException;

    void deleteSavedDraft(String sid, long draftId,
            AsyncCallback<BulkUploadInfo> callback) throws AuthenticationException;

    void createSample(String sessionId, SampleStorage sampleStorage, long entryId,
            AsyncCallback<SampleStorage> callback) throws AuthenticationException;

    void retrieveProfileInfo(String sid, String userId, AsyncCallback<User> callback)
            throws AuthenticationException;

    void retrieveCollections(String sessionId, AsyncCallback<ArrayList<FolderDetails>> callback);

    void updateFolder(String sid, long folderId, FolderDetails update,
            AsyncCallback<FolderDetails> callback) throws AuthenticationException;

    void createUserCollection(String sid, String name, String description,
            ArrayList<Long> arrayList, AsyncCallback<FolderDetails> callback)
            throws AuthenticationException;

    void moveToUserCollection(String sid, long source, ArrayList<Long> destination,
            ArrayList<Long> entryIds, AsyncCallback<ArrayList<FolderDetails>> callback)
            throws AuthenticationException;

    void addEntriesToCollection(String sid, ArrayList<Long> destination, ArrayList<Long> entryIds,
            AsyncCallback<ArrayList<FolderDetails>> callback) throws AuthenticationException;

    void updateEntry(String sid, PartData info, AsyncCallback<Long> callback) throws AuthenticationException;

    void retrieveStorageSchemes(String sessionId, EntryType type,
            AsyncCallback<HashMap<PartSample, ArrayList<String>>> callback);

    // news
    void retrieveNewsItems(String sessionId, AsyncCallback<ArrayList<NewsItem>> callback)
            throws AuthenticationException;

    void createNewsItem(String sessionId, NewsItem item, AsyncCallback<NewsItem> callback)
            throws AuthenticationException;

    // bulk import and draft
    void retrieveBulkImport(String sid, long id, int start, int limit, AsyncCallback<BulkUploadInfo> callback)
            throws AuthenticationException;

    void deleteFolder(String sessionId, long folderId, AsyncCallback<FolderDetails> callback);

    void addPermission(String sessionId, AccessPermission accessPermission,
            AsyncCallback<Boolean> callback) throws AuthenticationException;

    void removePermission(String sessionId, AccessPermission accessPermission,
            AsyncCallback<Boolean> callback) throws AuthenticationException;

    void saveSequence(String sessionId, PartData part, String sequenceUser, boolean isFile,
            AsyncCallback<PartData> callback) throws AuthenticationException;

    void getConfigurationSetting(String name, AsyncCallback<String> callback);

    void retrieveAccount(String email, AsyncCallback<User> callback);

    void createNewAccount(User info, boolean sendEmail, AsyncCallback<String> callback);

    void updateAccount(String sid, String email, User info,
            AsyncCallback<User> callback) throws AuthenticationException;

    void updateAccountPassword(String sid, String email, String password,
            AsyncCallback<Boolean> callback) throws AuthenticationException;

    void handleForgotPassword(String email, String url, AsyncCallback<Boolean> callback)
            throws AuthenticationException;

    void retrieveAllUserAccounts(String sid, int start, int limit, AsyncCallback<AccountResults> callback)
            throws AuthenticationException;

    void removeSequence(String sid, long entryId, AsyncCallback<Boolean> callback)
            throws AuthenticationException;

    void retrieveEntryTraceSequences(String sid, long entryId, AsyncCallback<ArrayList<SequenceAnalysisInfo>> callback)
            throws AuthenticationException;

    void deleteEntryTraceSequences(String sid, long entryId, ArrayList<String> seqId,
            AsyncCallback<ArrayList<SequenceAnalysisInfo>> callback) throws AuthenticationException;

    void deleteEntryAttachment(String sid, String fileId, AsyncCallback<Boolean> callback)
            throws AuthenticationException;

    void retrieveGroups(String sid, GroupType type, AsyncCallback<ArrayList<UserGroup>> callback)
            throws AuthenticationException;

    void retrieveGroupMembers(String sessionId, UserGroup user, AsyncCallback<ArrayList<User>> callback)
            throws AuthenticationException;

    void setGroupMembers(String sessionId, UserGroup user, ArrayList<User> members,
            AsyncCallback<ArrayList<User>> callback) throws AuthenticationException;

    void createNewGroup(String sessionId, UserGroup user,
            AsyncCallback<UserGroup> callback) throws AuthenticationException;

    void deleteEntry(String sessionId, PartData info, AsyncCallback<ArrayList<FolderDetails>> callback)
            throws AuthenticationException;

    void createEntry(String sid, PartData info, AsyncCallback<Long> async) throws AuthenticationException;

    void removeFromUserCollection(String sessionId, long source, ArrayList<Long> ids,
            AsyncCallback<FolderDetails> async) throws AuthenticationException;

    void approvePendingBulkImport(String sessionId, long id, AsyncCallback<Boolean> async);

    void submitBulkUploadDraft(String sid, long draftId, ArrayList<UserGroup> readGroups, AsyncCallback<Boolean> async);

    void getAutoCompleteSuggestion(AutoCompleteField field, SuggestOracle.Request request,
            AsyncCallback<SuggestOracle.Response> async);

    void retrieveSystemSettings(String sid, AsyncCallback<HashMap<String, String>> asyncCallback);

    void retrieveWebOfRegistryPartners(String sid, AsyncCallback<WebOfRegistries> asyncCallback)
            throws AuthenticationException;

    void setConfigurationSetting(String sid, ConfigurationKey key, String value, AsyncCallback<Boolean> async);

    void setPreferenceSetting(String sid, String key, String value, AsyncCallback<Boolean> async)
            throws AuthenticationException;

    void revertedSubmittedBulkUpload(String sid, long uploadId, AsyncCallback<Boolean> async);

    void retrieveAllVisibleEntrys(String sid, FolderDetails details, ColumnField field, boolean asc, int start,
            int limit, AsyncCallback<FolderDetails> async);

    void retrieveAvailableAccounts(String sessionId, AsyncCallback<ArrayList<User>> callback);

    void addWebPartner(String sessionId, String partnerName, String partnerUrl, AsyncCallback<Boolean> callback);

    void autoUpdateBulkUpload(String sid, BulkUploadAutoUpdate wrapper, EntryAddType addType,
            AsyncCallback<BulkUploadAutoUpdate> callback) throws AuthenticationException;

    void updateBulkUploadPreference(String sid, long bulkUploadId, EntryAddType addType, PreferenceInfo info,
            AsyncCallback<Long> callback) throws AuthenticationException;

    void retrieveUserPreferences(String sid, ArrayList<PreferenceKey> keys,
            AsyncCallback<HashMap<PreferenceKey, String>> async) throws AuthenticationException;

    void retrieveUserSearchPreferences(String sid, AsyncCallback<HashMap<String, String>> async)
            throws AuthenticationException;

    void isWebOfRegistriesEnabled(AsyncCallback<Boolean> async);

    void retrieveMessages(String sessionId, int start, int count, AsyncCallback<MessageList> callback)
            throws AuthenticationException;

    void setBulkUploadDraftName(String sessionId, long id, String draftName, AsyncCallback<Boolean> callback)
            throws AuthenticationException;

    void updateGroup(String sessionId, UserGroup user, AsyncCallback<UserGroup> async);

    void deleteGroup(String sessionId, UserGroup user, AsyncCallback<UserGroup> asyncCallback);

    void removeAccountFromGroup(String sessionId, UserGroup user, User account, AsyncCallback<Boolean> callback);

    void requestEntryTransfer(String sid, ArrayList<Long> ids, ArrayList<String> sites, AsyncCallback<Void> callback)
            throws AuthenticationException;

    void deleteSample(String sessionId, PartSample part, AsyncCallback<Boolean> async);

    void retrieveUserGroups(String sessionId, boolean includePublicGroup, AsyncCallback<ArrayList<UserGroup>> async);

    void promoteCollection(String sessionId, long id, AsyncCallback<Boolean> async);

    void demoteCollection(String sessionId, long id, AsyncCallback<Boolean> async);

    void sendMessage(String sid, MessageInfo info, AsyncCallback<Boolean> async);

    void markMessageRead(String sessionId, long id, AsyncCallback<Integer> async);

    void rebuildSearchIndex(String sessionId, IndexType type, AsyncCallback<Boolean> callback);

    void sendComment(String sid, UserComment comment, AsyncCallback<UserComment> callback);

    void requestSample(String sid, long entryId, SampleRequestType type, AsyncCallback<SampleRequest> callback);

    void alertToEntryProblem(String sid, long entryID, String details, AsyncCallback<UserComment> callback);

    void setEnableWebOfRegistries(String sessionId, boolean value, AsyncCallback<ArrayList<RegistryPartner>> callback)
            throws AuthenticationException;

    void retrieveTransferredParts(String sessionId, AsyncCallback<ArrayList<PartData>> callback)
            throws AuthenticationException;

    void processTransferredParts(String sid, ArrayList<Long> partIds, boolean accept, AsyncCallback<Boolean> async);

    void disablePublicReadAccess(String sid, long entryId, AsyncCallback<Boolean> async);

    void enablePublicReadAccess(String sid, long entryId, AsyncCallback<Boolean> async);

    void setPropagatePermissionForFolder(String sid, long folderId, boolean prop, AsyncCallback<Boolean> async);

    void exportParts(String sid, ArrayList<Long> partIds, ExportAsOption option, AsyncCallback<String> asyncCallback);

    void enableOrDisableFolderPublicAccess(String sid, long folderId, boolean isEnable, AsyncCallback<Boolean> async)
            throws AuthenticationException;

    void setRegistryPartnerStatus(String sid, RegistryPartner partner, AsyncCallback<RegistryPartner> async) throws
            AuthenticationException;

    void getBulkEditData(String sid, ArrayList<Long> partIds, AsyncCallback<BulkUploadInfo> async);

    void getSampleRequests(String sid, SampleRequestStatus status, AsyncCallback<ArrayList<SampleRequest>> async);

    void removeSampleRequestFromCart(String sid, long entryId, AsyncCallback<SampleRequest> async);

    void getAllPendingSampleRequests(String sid, AsyncCallback<ArrayList<SampleRequest>> async);

    void updateSampleRequest(String sessionId, SampleRequest request, AsyncCallback<SampleRequest> callback);

    void submitSampleRequests(String sessionId, ArrayList<SampleRequest> requests, AsyncCallback<Boolean> callback);
}