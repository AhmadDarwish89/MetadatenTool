spool P:\V-Modell\CCM\Betrieb\Vorlagenkonfigurationen\logs\.LOG

declare 
  lc_anlage_user_nr  edok_vorlage.anlage_user_nr%type := 'CCM-ADMIN';
  lc_anlage_orgnr    edok_vorlage.anlage_orgnr%type := 'CCM-ADMIN';
    
begin
-- Vorlagenkonfiguration für diese Vorlage löschen
delete from edok_vorlage_folgeschreiben_konfiguration where edok_vorlage_folgeschreiben_id between 222200 and 222299;
delete from edok_vorlagenbaum_vorlage where id between 222200 and 222299;
delete from edok_metadatum where id between 222200 and 222299;
delete from edok_vorlage where id between 222200 and 222299;

-- Vorlage in Tabelle edok_vorlage hinzufügen
insert into `edok_vorlage` (`id`, `bezeichnung`, `version`, 'url', 'vorlage_art_code', 'phase_code', 'geloescht', 'anlage_datum', 'anlage_user_nr', 'anlage_orgnr', 'technische_bezeichnung') 
values ('222220', '', '', '', 'a', 'a', '', 'sysdate', 'CCM-ADMIN', 'CCM-ADMIN', '');
