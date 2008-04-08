package com.myJava.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

/**
 * Classe destin�e � g�rer les inputs de ligne de commande.
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 6668125177615540854
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

public class CommandLineManager {
    protected String clmPrompt;
    protected Hashtable clmCommands;
    protected boolean clmShallStop;
    protected BufferedReader clmKeyboardReader;


    /**
     * Constructeur - initialise la map des commandes
     */
    public CommandLineManager() {
        this.clmCommands = new Hashtable();
        this.clmKeyboardReader = new BufferedReader(new InputStreamReader(System.in));
        this.setPrompt("> ");

        this.addCommand("exit", "cmdExit");
        this.addCommand("echo", "cmdEcho");
    }

    /**
     * Sp�cifie le prompt
     */
    public void setPrompt(String prompt) {
        this.clmPrompt = prompt;
    }

    /**
     * Ajoute une commande au gestionnaire.
     * La m�thode doit avoir pour signature :
     * void myMethod(String, String)
     */
    public void addCommand(String command, String method) {
        this.clmCommands.put(command, method);
    }

    /**
     * Lance l'invite
     */
    public void startPrompt() {
        this.clmShallStop = false;

        while(! this.clmShallStop) {

            // On affiche le prompt et on lit l'entr�e
            this.nl();
            this.nl();
            this.display(this.clmPrompt);
            String typedText = this.readCommand();

            if (typedText != null && typedText.length() != 0) {
                int index = typedText.indexOf(" ");

                // On isole la commande et les arguments
                String command = typedText;
                String args = "";
                if (index != -1) {
                    command = typedText.substring(0, index);
                    args = typedText.substring(index+1);
                }

                // On recherche la commande
                Method m = this.getCorrespondingMethod(command);
                if (m == null) {
                    this.displayCommandNotFound();
                } else {
                    // Init des arguments
                    Object[] argsArray = null;
                    if (m.getParameterTypes().length != 0) {
                        argsArray = new Object[1];
                        argsArray[0] = args;
                    }

                    // On invoque la commande
                    try {
                        m.invoke(this, argsArray);
                    } catch (InvocationTargetException e) {
                        this.displayCommandNotFound();
                    } catch (IllegalAccessException e) {
                        this.displayCommandNotFound();
                    }
                }
            }
        }
    }

    /**
     * Arr�te le manager
     * Respecte la signature d'une LineCommand
     */
    public void cmdExit() {
        this.clmShallStop = true;
    }

    /**
     * Commande "Echo"
     */
    public void cmdEcho(String arg) {
        this.display(arg, true);
    }

    /**
     * Pour tests
     */
    public static void main(String[] args) {
        CommandLineManager cc = new CommandLineManager();
        cc.setPrompt(">");
        cc.startPrompt();
    }

    /**
     * Retourne la m�thode qui va traiter la commande
     * Recherche tout d'abord une m�thode avec la signature
     * void myMethod(String)
     *
     * Puis
     *
     * void myMethod()
     */
    protected Method getCorrespondingMethod(String commandName) {
        String methodName = (String)this.clmCommands.get(commandName);
        if (methodName == null) {
            return null;
        } else {
            Class[] argsClasses = new Class[1];
            argsClasses[0] = "".getClass();
            try {
                return this.getClass().getMethod(methodName, argsClasses);
            } catch (NoSuchMethodException e) {
                try {
                    return this.getClass().getMethod(methodName, null);
                } catch (NoSuchMethodException e2) {
                    return null;
                }
            }
        }
    }

    /**
     * Lecture d'une entr�e user
     */
    protected String readCommand() {
        try {
                return this.clmKeyboardReader.readLine();
        } catch( IOException e ) {
                e.printStackTrace();
                return null;
        }
    }

    /**
     * Affiche le message
     */
    protected void display(String text) {
        this.display(text, false);
    }

    /**
     * Affiche le message
     */
    protected void display(String text, boolean cr) {
        System.out.print(text);
        if (cr) {
            System.out.print("\n");
        }
    }

    /**
     * Affiche ligne vierge
     */
    protected void nl() {
        System.out.println();
    }

    /**
     * Affichage d'une erreur
     */
    protected void displayError(String text) {
        this.display("--[" + text + "]--");
        this.nl();
    }

    /**
     * Affiche un message d'erreur "command not found"
     */
    protected void displayCommandNotFound() {
        this.displayError("Command not found");
    }
}