package org.jbei.ice.lib.entry.sample;

import org.jbei.ice.controllers.ApplicationController;
import org.jbei.ice.controllers.common.ControllerException;
import org.jbei.ice.lib.account.model.Account;
import org.jbei.ice.lib.dao.DAOException;
import org.jbei.ice.lib.entry.model.Entry;
import org.jbei.ice.lib.entry.sample.model.Sample;
import org.jbei.ice.lib.entry.sample.model.Storage;
import org.jbei.ice.lib.logging.Logger;
import org.jbei.ice.lib.permissions.PermissionException;
import org.jbei.ice.lib.permissions.PermissionsController;
import org.jbei.ice.lib.utils.Utils;
import org.jbei.ice.shared.ColumnField;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

/**
 * ABI to manipulate {@link Sample}s.
 *
 * @author Timothy Ham, Zinovii Dmytriv, Hector Plahar
 */
public class SampleController {
    private final SampleDAO dao;
    private final PermissionsController permissionsController;

    public SampleController() {
        dao = new SampleDAO();
        permissionsController = new PermissionsController();
    }

    /**
     * Create a {@link Sample} object.
     * <p/>
     * Generates the UUID and the time stamps.
     *
     * @param label
     * @param depositor
     * @param notes
     * @return {@link Sample}
     */
    public Sample createSample(String label, String depositor, String notes) {
        return createSample(label, depositor, notes, Utils.generateUUID(), Calendar.getInstance()
                                                                                   .getTime(), null);
    }

    /**
     * Create a {@link Sample} object.
     *
     * @param label
     * @param depositor
     * @param notes
     * @param uuid
     * @param creationTime
     * @param modificationTime
     * @return {@link Sample}
     */
    public Sample createSample(String label, String depositor, String notes, String uuid,
            Date creationTime, Date modificationTime) {
        Sample sample = new Sample();

        sample.setLabel(label);
        sample.setDepositor(depositor);
        sample.setNotes(notes);
        sample.setUuid(uuid);
        sample.setCreationTime(creationTime);
        sample.setModificationTime(modificationTime);

        return sample;
    }

    /**
     * Checks if the user has write permission of the {@link Sample}.
     *
     * @param sample
     * @return True if user has write permission.
     * @throws ControllerException
     */
    public boolean hasWritePermission(Account account, Sample sample) throws ControllerException {
        if (sample == null || sample.getEntry() == null) {
            throw new ControllerException("Failed to check write permissions for null sample!");
        }

        return permissionsController.hasWritePermission(account, sample.getEntry());
    }

    /**
     * Save the {@link Sample} into the database, then rebuilds the search index.
     *
     * @param sample
     * @return Saved sample.
     * @throws ControllerException
     * @throws PermissionException
     */
    public Sample saveSample(Account account, Sample sample) throws ControllerException, PermissionException {
        return saveSample(account, sample, true);
    }

    /**
     * Save the {@link Sample} into the database, with the option to rebuild the search index.
     *
     * @param sample
     * @param scheduleIndexRebuild
     * @return saved sample.
     * @throws ControllerException
     * @throws PermissionException
     */
    public Sample saveSample(Account account, Sample sample, boolean scheduleIndexRebuild)
            throws ControllerException, PermissionException {
        if (!hasWritePermission(account, sample)) {
            throw new PermissionException("No permissions to save sample!");
        }

        Sample savedSample = null;

        try {
            savedSample = dao.save(sample);

            if (scheduleIndexRebuild) {
                ApplicationController.scheduleSearchIndexRebuildJob();
            }
        } catch (DAOException e) {
            throw new ControllerException(e);
        }

        return savedSample;
    }

    /**
     * Delete the {@link Sample} in the database, then rebuild the search index. Also deletes the
     * associated {@link Storage}, if it is a tube.
     *
     * @param sample
     * @throws ControllerException
     * @throws PermissionException
     */
    public void deleteSample(Account account, Sample sample) throws ControllerException, PermissionException {
        deleteSample(account, sample, true);
    }

    /**
     * Delete the {@link Sample} in the database, with the option to rebuild the search index. Also
     * deletes the associated {@link Storage}, if it is a tube.
     *
     * @param sample
     * @param scheduleIndexRebuild
     * @throws ControllerException
     * @throws PermissionException
     */
    public void deleteSample(Account account, Sample sample, boolean scheduleIndexRebuild)
            throws ControllerException, PermissionException {
        if (!hasWritePermission(account, sample)) {
            throw new PermissionException("No permissions to delete sample!");
        }

        try {
            Storage storage = sample.getStorage();

            dao.deleteSample(sample);

            if (storage.getStorageType() == Storage.StorageType.TUBE) {
                dao.delete(storage);
            }

            if (scheduleIndexRebuild) {
                ApplicationController.scheduleSearchIndexRebuildJob();
            }
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }

    /**
     * Retrieve the number of {@link Sample}s associated with the {@link Entry}.
     *
     * @param entry
     * @return Number of samples associated with the entry.
     * @throws ControllerException
     */
    public long getNumberOfSamples(Entry entry) throws ControllerException {
        long result = 0;
        try {
            ArrayList<Sample> samples = dao.getSamplesByEntry(entry);

            result = (samples == null) ? 0 : samples.size();
        } catch (DAOException e) {
            throw new ControllerException(e);
        }

        return result;
    }

    /**
     * Retrieve the {@link Sample}s associated with the {@link Entry}.
     *
     * @param entry
     * @return ArrayList of {@link Sample}s.
     * @throws ControllerException
     */
    public ArrayList<Sample> getSamples(Entry entry) throws ControllerException {
        ArrayList<Sample> samples = null;

        try {
            samples = dao.getSamplesByEntry(entry);
        } catch (DAOException e) {
            throw new ControllerException(e);
        }

        return samples;
    }

    /**
     * Retrieve the {@link Sample}s associated with the given depositor's email.
     *
     * @param depositorEmail
     * @param offset
     * @param limit
     * @return ArrayList of {@link Sample}s.
     * @throws ControllerException
     */
    public ArrayList<Sample> getSamplesByDepositor(String depositorEmail, int offset, int limit)
            throws ControllerException {
        ArrayList<Sample> samples = null;

        try {
            samples = dao.getSamplesByDepositor(depositorEmail, offset, limit);
        } catch (DAOException e) {
            throw new ControllerException(e);
        }

        return samples;
    }

    /**
     * Retrieve the {@link Sample}s associated with the given {@link Storage}.
     *
     * @param storage
     * @return ArrayList of {@link Sample}s.
     * @throws ControllerException
     */
    public ArrayList<Sample> getSamplesByStorage(Storage storage) throws ControllerException {

        try {
            return dao.getSamplesByStorage(storage);
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }

    /**
     * Retrieve the number of {@link Sample}s by the given depositor's email.
     *
     * @param depositorEmail
     * @return Number of {@link Sample}s.
     * @throws ControllerException
     */
    public int getNumberOfSamplesByDepositor(String depositorEmail) throws ControllerException {

        try {
            return dao.getSampleCountBy(depositorEmail);
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }

    public LinkedList<Long> retrieveSamplesByDepositor(String email, ColumnField field, boolean asc)
            throws ControllerException {

        LinkedList<Long> results = null;
        try {
            switch (field) {

                default:
                case CREATED:
                    results = dao.retrieveSamplesByDepositorSortByCreated(email, asc);
                    //                getSamplePermissionVerifier().hasReadPermissions(model, account) // TODO
                    break;
            }
        } catch (DAOException e) {
            throw new ControllerException(e);
        }

        return results;
    }

    public LinkedList<Sample> retrieveSamplesByIdSet(LinkedList<Long> ids, boolean asc)
            throws ControllerException {
        try {
            return dao.getSamplesByIdSet(ids, asc);
        } catch (DAOException e) {
            Logger.error(e);
            return null;
        }
    }

    public ArrayList<Sample> getSamplesByEntry(Entry entry) throws ControllerException {
        try {
            return dao.getSamplesByEntry(entry);
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }

    public int getSampleCountBy(String email) throws ControllerException {
        try {
            return dao.getSampleCountBy(email);
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }

    public boolean hasSample(Entry entry) throws ControllerException {
        try {
            return dao.hasSample(entry);
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }
}