/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelos.Palabra;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelos.Tipo;

/**
 *
 * @author eliel
 */
public class TipoJpaController implements Serializable {

    public TipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipo tipo) {
        if (tipo.getPalabraCollection() == null) {
            tipo.setPalabraCollection(new ArrayList<Palabra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Palabra> attachedPalabraCollection = new ArrayList<Palabra>();
            for (Palabra palabraCollectionPalabraToAttach : tipo.getPalabraCollection()) {
                palabraCollectionPalabraToAttach = em.getReference(palabraCollectionPalabraToAttach.getClass(), palabraCollectionPalabraToAttach.getIdpalabra());
                attachedPalabraCollection.add(palabraCollectionPalabraToAttach);
            }
            tipo.setPalabraCollection(attachedPalabraCollection);
            em.persist(tipo);
            for (Palabra palabraCollectionPalabra : tipo.getPalabraCollection()) {
                Tipo oldTipoOfPalabraCollectionPalabra = palabraCollectionPalabra.getTipo();
                palabraCollectionPalabra.setTipo(tipo);
                palabraCollectionPalabra = em.merge(palabraCollectionPalabra);
                if (oldTipoOfPalabraCollectionPalabra != null) {
                    oldTipoOfPalabraCollectionPalabra.getPalabraCollection().remove(palabraCollectionPalabra);
                    oldTipoOfPalabraCollectionPalabra = em.merge(oldTipoOfPalabraCollectionPalabra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipo tipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo persistentTipo = em.find(Tipo.class, tipo.getId());
            Collection<Palabra> palabraCollectionOld = persistentTipo.getPalabraCollection();
            Collection<Palabra> palabraCollectionNew = tipo.getPalabraCollection();
            List<String> illegalOrphanMessages = null;
            for (Palabra palabraCollectionOldPalabra : palabraCollectionOld) {
                if (!palabraCollectionNew.contains(palabraCollectionOldPalabra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Palabra " + palabraCollectionOldPalabra + " since its tipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Palabra> attachedPalabraCollectionNew = new ArrayList<Palabra>();
            for (Palabra palabraCollectionNewPalabraToAttach : palabraCollectionNew) {
                palabraCollectionNewPalabraToAttach = em.getReference(palabraCollectionNewPalabraToAttach.getClass(), palabraCollectionNewPalabraToAttach.getIdpalabra());
                attachedPalabraCollectionNew.add(palabraCollectionNewPalabraToAttach);
            }
            palabraCollectionNew = attachedPalabraCollectionNew;
            tipo.setPalabraCollection(palabraCollectionNew);
            tipo = em.merge(tipo);
            for (Palabra palabraCollectionNewPalabra : palabraCollectionNew) {
                if (!palabraCollectionOld.contains(palabraCollectionNewPalabra)) {
                    Tipo oldTipoOfPalabraCollectionNewPalabra = palabraCollectionNewPalabra.getTipo();
                    palabraCollectionNewPalabra.setTipo(tipo);
                    palabraCollectionNewPalabra = em.merge(palabraCollectionNewPalabra);
                    if (oldTipoOfPalabraCollectionNewPalabra != null && !oldTipoOfPalabraCollectionNewPalabra.equals(tipo)) {
                        oldTipoOfPalabraCollectionNewPalabra.getPalabraCollection().remove(palabraCollectionNewPalabra);
                        oldTipoOfPalabraCollectionNewPalabra = em.merge(oldTipoOfPalabraCollectionNewPalabra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipo.getId();
                if (findTipo(id) == null) {
                    throw new NonexistentEntityException("The tipo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo tipo;
            try {
                tipo = em.getReference(Tipo.class, id);
                tipo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Palabra> palabraCollectionOrphanCheck = tipo.getPalabraCollection();
            for (Palabra palabraCollectionOrphanCheckPalabra : palabraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipo (" + tipo + ") cannot be destroyed since the Palabra " + palabraCollectionOrphanCheckPalabra + " in its palabraCollection field has a non-nullable tipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipo> findTipoEntities() {
        return findTipoEntities(true, -1, -1);
    }

    public List<Tipo> findTipoEntities(int maxResults, int firstResult) {
        return findTipoEntities(false, maxResults, firstResult);
    }

    private List<Tipo> findTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Tipo findTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipo> rt = cq.from(Tipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
