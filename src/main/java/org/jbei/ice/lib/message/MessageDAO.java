package org.jbei.ice.lib.message;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jbei.ice.lib.account.model.Account;
import org.jbei.ice.lib.dao.DAOException;
import org.jbei.ice.lib.dao.hibernate.HibernateRepository;
import org.jbei.ice.lib.group.Group;
import org.jbei.ice.lib.logging.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Hibernate DAO for {@link Message}
 *
 * @author Hector Plahar
 */
@SuppressWarnings("unchecked")
public class MessageDAO extends HibernateRepository<Message> {

    public Message retrieveMessage(long id) throws DAOException {
        return super.get(Message.class, id);
    }

    public int retrieveNewMessageCount(Account account) throws DAOException {
        try {
            Session session = currentSession();
            StringBuilder builder = new StringBuilder();
            builder.append("select count(id) from message m where m.is_read=false AND (m.id in ")
                    .append("(select message_id from message_destination_accounts where account_id = ")
                    .append(account.getId())
                    .append(")");

            if (!account.getGroups().isEmpty()) {
                builder.append(" OR m.id in (select message_id from message_destination_groups where group_id in (");

                int size = account.getGroups().size();
                int i = 0;
                for (Group group : account.getGroups()) {
                    builder.append(group.getId());
                    if (i < size - 1)
                        builder.append(", ");
                }

                builder.append("))");
            }
            builder.append(")");
            Query query = session.createSQLQuery(builder.toString());
            Number number = (Number) query.uniqueResult();
            return number.intValue();
        } catch (HibernateException he) {
            Logger.error(he);
            throw new DAOException(he);
        }
    }

    public Set<Message> retrieveMessages(Account account, int start, int count) throws DAOException {
        try {
            Session session = currentSession();
            StringBuilder builder = new StringBuilder();
            builder.append("select id from message m where m.id in ")
                    .append("(select message_id from message_destination_accounts where account_id = ")
                    .append(account.getId())
                    .append(")");

            if (!account.getGroups().isEmpty()) {
                builder.append(" OR m.id in (select message_id from message_destination_groups where group_id in (");

                int size = account.getGroups().size();
                int i = 0;
                for (Group group : account.getGroups()) {
                    builder.append(group.getId());
                    if (i < size - 1)
                        builder.append(", ");
                }

                builder.append("))");
            }

            Query query = session.createSQLQuery(builder.toString());
            query.setFirstResult(start);
            query.setMaxResults(count);
            List list = query.list();
            Set<Long> set = new HashSet<>();
            for (Object object : list) {
                Number number = (Number) object;
                set.add(number.longValue());
            }

            if (set.isEmpty())
                return new HashSet<>();

            Criteria criteria = session.createCriteria(Message.class).add(Restrictions.in("id", set));
            criteria.addOrder(Order.desc("dateSent"));
            Set<Message> results = new HashSet<Message>(criteria.list());
            return results;
        } catch (HibernateException he) {
            Logger.error(he);
            throw new DAOException(he);
        }
    }

    public int retrieveMessageCount(Account account) throws DAOException {
        try {
            Session session = currentSession();
            StringBuilder builder = new StringBuilder();
            builder.append("select count(id) from message m where m.id in ")
                    .append("(select message_id from message_destination_accounts where account_id = ")
                    .append(account.getId())
                    .append(")");

            if (!account.getGroups().isEmpty()) {
                builder.append(" OR m.id in (select message_id from message_destination_groups where group_id in")
                        .append(" (");

                int size = account.getGroups().size();
                int i = 0;
                for (Group group : account.getGroups()) {
                    builder.append(group.getId());
                    if (i < size - 1)
                        builder.append(", ");
                }

                builder.append("))");
            }

            Query query = session.createSQLQuery(builder.toString());
            Number number = (Number) query.uniqueResult();
            return number.intValue();
        } catch (HibernateException he) {
            Logger.error(he);
            throw new DAOException();
        }
    }
}
