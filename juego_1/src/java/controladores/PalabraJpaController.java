/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelos.Palabra;
import modelos.Tipo;

/**
 *
 * @author eliel
 */
public class PalabraJpaController implements Serializable {

    public PalabraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Palabra palabra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo tipo = palabra.getTipo();
            if (tipo != null) {
                tipo = em.getReference(tipo.getClass(), tipo.getId());
                palabra.setTipo(tipo);
            }
            em.persist(palabra);
            if (tipo != null) {
                tipo.getPalabraCollection().add(palabra);
                tipo = em.merge(tipo);
            }
            em.getTransaction().commit();
        }catch(Exception e){
            System.out.println("erro:  "+e.getMessage());
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return true;
    }

    public void edit(Palabra palabra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Palabra persistentPalabra = em.find(Palabra.class, palabra.getIdpalabra());
            Tipo tipoOld = persistentPalabra.getTipo();
            Tipo tipoNew = palabra.getTipo();
            if (tipoNew != null) {
                tipoNew = em.getReference(tipoNew.getClass(), tipoNew.getId());
                palabra.setTipo(tipoNew);
            }
            palabra = em.merge(palabra);
            if (tipoOld != null && !tipoOld.equals(tipoNew)) {
                tipoOld.getPalabraCollection().remove(palabra);
                tipoOld = em.merge(tipoOld);
            }
            if (tipoNew != null && !tipoNew.equals(tipoOld)) {
                tipoNew.getPalabraCollection().add(palabra);
                tipoNew = em.merge(tipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = palabra.getIdpalabra();
                if (findPalabra(id) == null) {
                    throw new NonexistentEntityException("The palabra with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Palabra palabra;
            try {
                palabra = em.getReference(Palabra.class, id);
                palabra.getIdpalabra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The palabra with id " + id + " no longer exists.", enfe);
            }
            Tipo tipo = palabra.getTipo();
            if (tipo != null) {
                tipo.getPalabraCollection().remove(palabra);
                tipo = em.merge(tipo);
            }
            em.remove(palabra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Palabra> findPalabraEntities() {
        return findPalabraEntities(true, -1, -1);
    }

    public List<Palabra> findPalabraEntities(int maxResults, int firstResult) {
        return findPalabraEntities(false, maxResults, firstResult);
    }

    private List<Palabra> findPalabraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Palabra.class));
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

    public Palabra findPalabra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Palabra.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Palabra> findPalabra2(String palabra){
        EntityManager em = getEntityManager();
        try{
            Query q = em.createQuery("SELECT a from Palabra a where a.palabra like :palabra");
            q.setParameter("palabra", palabra);
            List<Palabra> palabras = (List<Palabra>) q.getResultList();
            return palabras;
        }finally{
            em.close();
        }
    }

    public int getPalabraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Palabra> rt = cq.from(Palabra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
