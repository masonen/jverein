/**********************************************************************
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright (c) by Heiner Jostkleigrewe
 * This program is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without 
 *  even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See 
 *  the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, 
 * see <http://www.gnu.org/licenses/>.
 * 
 * heiner@jverein.de
 * www.jverein.de
 **********************************************************************/
package de.jost_net.JVerein.server;

import java.rmi.RemoteException;

import de.jost_net.JVerein.JVereinPlugin;
import de.jost_net.JVerein.rmi.Mail;
import de.jost_net.JVerein.rmi.MailAnhang;
import de.willuhn.datasource.db.AbstractDBObject;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

public class MailAnhangImpl extends AbstractDBObject implements MailAnhang,
    Comparable<MailAnhang>
{

  private static final long serialVersionUID = 1L;

  public MailAnhangImpl() throws RemoteException
  {
    super();
  }

  @Override
  protected String getTableName()
  {
    return "mailanhang";
  }

  @Override
  public String getPrimaryAttribute()
  {
    return "id";
  }

  @Override
  protected void deleteCheck()
  {
    //
  }

  @Override
  protected void insertCheck() throws ApplicationException
  {
    // try
    // {
    // if (getBetreff() == null || getBetreff().length() == 0)
    // {
    // throw new ApplicationException(JVereinPlugin.getI18n().tr(
    // "Bitte Betreff eingeben"));
    // }
    // }
    // catch (RemoteException e)
    // {
    // Logger.error("insert check of mailvorlage failed", e);
    // throw new ApplicationException(JVereinPlugin.getI18n().tr(
    // "MailVorlage kann nicht gespeichert werden. Siehe system log"));
    // }
    try
    {
      dateinameCheck(getDateiname());
    }
    catch (RemoteException e)
    {
      String fehler = JVereinPlugin.getI18n().tr(
          "MailVorlage kann nicht gespeichert werden. Siehe system log");
      Logger.error(fehler, e);
      throw new ApplicationException(fehler);
    }
    
  }
  
  private void dateinameCheck(String dateiname) throws ApplicationException
  {
  //L�nge des Dateinamens auf 50 Zeichen begrenzt:
    //JVereinUpdateProvider: update0093(Connection)
    if(dateiname.length()>50)
      throw new ApplicationException(JVereinPlugin.getI18n().tr(
          "Maximale L�nge (50) von Mail-Anhang �berschritten. ("+dateiname.length()+", "+dateiname.substring(0, 50)+"...)"));
  }

  @Override
  protected void updateCheck() throws ApplicationException
  {
    insertCheck();
  }
  
  
  @Override
  protected Class<?> getForeignObject(String arg0)
  {
    if ("mail".equals(arg0))
    {
      return Mail.class;
    }
    return null;
  }

  public Mail getMail() throws RemoteException
  {
    return (Mail) getAttribute("mail");
  }

  public void setMail(Mail mail) throws RemoteException
  {
    setAttribute("mail", mail);
  }

  public String getDateiname() throws RemoteException
  {
    return (String) getAttribute("dateiname");
  }

  public void setDateiname(String dateiname) throws RemoteException
  {
    setAttribute("dateiname", dateiname);
  }

  public byte[] getAnhang() throws RemoteException
  {
    return (byte[]) this.getAttribute("anhang");
  }

  public void setAnhang(byte[] anhang) throws RemoteException
  {
    setAttribute("anhang", anhang);
  }

  @Override
  public Object getAttribute(String fieldName) throws RemoteException
  {
    return super.getAttribute(fieldName);
  }

  public int compareTo(MailAnhang o)
  {
    try
    {
      return getDateiname().compareTo(o.getDateiname());
    }
    catch (RemoteException e)
    {
      //
    }
    return 0;
  }

}
