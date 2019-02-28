/*
 * (c) Kitodo. Key to digital objects e. V. <contact@kitodo.org>
 *
 * This file is part of the Kitodo project.
 *
 * It is licensed under GNU General Public License version 3 or later.
 *
 * For the full copyright and license information, please read the
 * GPL3-License.txt file that was distributed with this source code.
 */

package org.kitodo.data.database.persistence;

import java.util.Collections;
import java.util.List;

import org.kitodo.data.database.beans.Workflow;
import org.kitodo.data.database.exceptions.DAOException;

public class WorkflowDAO extends BaseDAO<Workflow> {

    private static final long serialVersionUID = 1913256950316879121L;

    @Override
    public Workflow getById(Integer id) throws DAOException {
        Workflow result = retrieveObject(Workflow.class, id);
        if (result == null) {
            throw new DAOException("Object can not be found in database");
        }
        return result;
    }

    @Override
    public List<Workflow> getAll() throws DAOException {
        return retrieveAllObjects(Workflow.class);
    }

    @Override
    public List<Workflow> getAll(int offset, int size) throws DAOException {
        return retrieveObjects("FROM Workflow ORDER BY id", offset, size);
    }

    @Override
    public List<Workflow> getAllNotIndexed(int offset, int size) throws DAOException {
        return retrieveObjects("FROM Workflow WHERE indexAction = 'INDEX' OR indexAction IS NULL ORDER BY id ASC",
            offset, size);
    }

    @Override
    public void remove(Integer id) throws DAOException {
        removeObject(Workflow.class, id);
    }

    /**
     * Get available workflows - available means that workflow has status active and is
     * assigned to client with given id.
     * 
     * @param clientId
     *            id of client to which searched workflows should be assigned
     * @return list of available Workflow objects
     */
    public List<Workflow> getAvailableWorkflows(int clientId) {
        return getByQuery(
            "SELECT w FROM Workflow AS w INNER JOIN w.client AS c WITH c.id = :clientId WHERE w.status = 'ACTIVE'",
            Collections.singletonMap("clientId", clientId));
    }
}
