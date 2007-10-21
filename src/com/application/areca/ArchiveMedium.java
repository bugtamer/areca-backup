package com.application.areca;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import com.application.areca.context.ProcessContext;
import com.application.areca.indicator.IndicatorMap;
import com.application.areca.metadata.manifest.Manifest;
import com.application.areca.search.SearchCriteria;
import com.application.areca.search.TargetSearchResult;
import com.myJava.object.PublicClonable;
import com.myJava.util.errors.ActionReport;
import com.myJava.util.history.History;

/**
 * <BR>Interface d�finissant un support de stockage et pouvant h�berger des archives.
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 5653799526062900358
 */
 
 /*
 Copyright 2005-2007, Olivier PETRUCCI.
 
This file is part of Areca.

    Areca is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Areca is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Areca; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
public interface ArchiveMedium extends PublicClonable {

    /**
     * V�rifie l'�tat du syst�me avant l'action sp�cifi�e (archivage, backup, fusion) 
     */
    public ActionReport checkMediumState(int action);
    
    /**
     * Stocke une entr�e sur le support
     */
    public void store(RecoveryEntry entry, ProcessContext context) throws StoreException, ApplicationException;
    
    /**
     * Fusionne les archives jusqu'� la date donn�e.
     * <BR>Le comportement de cette m�thode varie avec l'impl�mentation.
     * <BR>Cette m�thode sert � �liminer des informations redondantes �ventuellement stock�es au
     * fil des archivages. 
     */
    public void merge(
            GregorianCalendar fromDate, 
            GregorianCalendar toDate, 
            boolean keepDeletedEntries,
            Manifest manifest,
            ProcessContext context
    ) throws ApplicationException;

    /**
     * Supprime les archives comprises entre "fromDate" et la date courante
     */
    public void deleteArchives(
            GregorianCalendar fromDate,
            ProcessContext context            
    ) throws ApplicationException;
    
    /**
     * Restaure l'archive correspondant � la date. Les informations sont restaur�es � l'emplacement
     * d�sign� par "destination". 
     */
    public void recover(
            Object destination, 
            String[] filter,
            GregorianCalendar date,
            boolean recoverDeletedEntries,
            ProcessContext context            
    ) throws ApplicationException;
    
    /**
     * D�truit toutes les archives et donn�es les concernant.
     */
    public void destroyRepository() throws ApplicationException;
    
    /**
     * Ouvre le support afin qu'il puisse h�berger par la suite des entr�es.
     */    
    public void open(ProcessContext context) throws ApplicationException;
    
    /**
     * Ouvre le support avec le manifest sp�cifi�.
     * Ceci signifie que les entr�es ajout�es le seront avec ce manifest.
     * Selon le mode de traitement du support, ce manifest peut �craser un manifest �ventuellement
     * existant, ou au contraire le compl�ter.
     */
    public void open(Manifest manifest, ProcessContext context) throws ApplicationException;
    
    /**
     * Valide le backup
     */
    public void commitBackup(ProcessContext context) throws ApplicationException;
    
    /**
     * Annule le backup
     */
    public void rollbackBackup(ProcessContext context) throws ApplicationException;
    
    /**
     * Valide le merge
     */
    public void commitMerge(ProcessContext context) throws ApplicationException;
    
    /**
     * Annule le merge
     */
    public void rollbackMerge(ProcessContext context) throws ApplicationException;
    
    /**
     * Retourne la cible � laquelle est affect� le support 
     */
    public AbstractRecoveryTarget getTarget();
    
    /**
     * Retourne une description du support de stockage 
     */
    public String getDescription();
    
    /**
     * Retourne l'historique des op�rations effectu�es sur le support 
     */
    public History getHistory();
    
    /**
     * Retourne le manifeste de l'archive correspondant � la date demand�e. 
     */
    public Manifest getManifest(GregorianCalendar date) throws ApplicationException;
    
    /**
     * Retourne les entr�es contenues dans l'archive pour la date donn�e. (sous forme de RecoveryEntries)
     */
    public Set getEntries(GregorianCalendar date) throws ApplicationException;
    
    public Set getLogicalView() throws ApplicationException;
    
    /**
     * Retourne l'historique d'une entr�e donn�e
     */
    public EntryArchiveData[] getHistory(RecoveryEntry entry) throws ApplicationException;
    
    /**
     * Appel�e avant la suppression de la target � laquelle appartient le medium
     */
    public void doBeforeDelete();
    
    /**
     * Appel�e apr�s la suppression de la target � laquelle appartient le medium
     */
    public void doAfterDelete();
    
    /**
     * Simule le traitement d'une RecoveryEntry durant un backup en mettant � jour son status.
     * Permet � la target de simuler un backup.
     */
    public void simulateEntryProcessing(RecoveryEntry entry, ProcessContext context) throws ApplicationException;
    
    /**
     * Closes the simulation and returns all unprocessed entries (ie entries which have been deleted). 
     */
    public List closeSimulation(ProcessContext context) throws ApplicationException;
    
    /**
     * Restaure la version demand�e (identifi�e par sa date) de l'entr�e sp�cifi�e � l'emplacement sp�cifi�  
     */
    public void recoverEntry(
            GregorianCalendar date, 
            RecoveryEntry entryToRecover, 
            Object destination,
            ProcessContext context            
    ) throws ApplicationException;

    /**
     * Indique s'il est utile de faire une pr�v�rification avant de d�clencher le backup.
     * <BR>Cette m�thode sera par exemple utile pour les supports de stockage incr�mentaux : ceci
     * leur permet de v�rifier qu'au moins un fichier a �t� modifi� avant de lancer le backup.
     */
    public boolean isPreBackupCheckUseful();
    
    /**
     * Computes indicators on the archives stored by the medium.
     */
    public IndicatorMap computeIndicators() throws ApplicationException;
    
    /**
     * Searches entries within the archives 
     */
    public TargetSearchResult search(SearchCriteria criteria) throws ApplicationException;
    
    public void install() throws ApplicationException;
    
    public void setTarget(AbstractRecoveryTarget target, boolean revalidate);
}