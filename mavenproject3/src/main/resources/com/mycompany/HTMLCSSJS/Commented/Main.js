         /*
         * Copyright (C) 2022 Hasan Barzegaravval
         *
         * This program is free software: you can redistribute it and/or modify
         * it under the terms of the GNU General Public License as published by
         * the Free Software Foundation, either version 3 of the License, or
         * (at your option) any later version.
         *
         * This program is distributed in the hope that it will be useful,
         * but WITHOUT ANY WARRANTY; without even the implied warranty of
         * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
         * GNU General Public License for more details.
         *
         * You should have received a copy of the GNU General Public License
         * along with this program.  If not, see <http://www.gnu.org/licenses/>.
         */
         /*
         * @author  Hasan Barzegaravval
         * @see     Lib.js,Main.js,CSS.css
         * @version 1.0
         * @since   1.0
         */
        'use strict';
        function main(){
           let education=Data.Education.map(function(e){return (new Education(e)).Create();});
           let WorkExperience=Data.WorkExperience.map(function(e){return Work(e);});
           let Languages=Data.Languages.map(function(e){return (new Language(e)).Create();});
           let Skills=new Skill(Data.Skills);
           let References=Data.References.map(function(e){return (new Reference(e)).Create();});
           let HEAD=new Header(Data.Header);
           HEAD.ToSHow();
           let El0=new Element({tag:"h2",Class:"SectionHeader",Text:"Personal Profile"},(new Profile(Data.Profile)).Create(),["Element","main"]).ToSHow();
           let El=new Element({tag:"h2",Class:"SectionHeader",Text:"Education"},education,["Element","main"]).ToSHow();
           let El2=new Element({tag:"h2",Class:"SectionHeader",Text:"Work Experience"},WorkExperience,["Element","main"]).ToSHow();
           let El3=new Element({tag:"h2",Class:"SectionHeader",Text:"Skills"},Skills.Create(),["Element","main"]).ToSHow();
           let El4=new Element({tag:"h2",Class:"SectionHeader",Text:"Languages"},Languages,["Element","main"]).ToSHow();
           let CitePattern={"JOUR":["Authors","Title1","Journal","Year"],"BOOK":["Authors","Title1","Year","PB"],"CHAP":["Authors","Title1","Title2","Year","PB"],"CONF":["Authors","Title1","Journal","Year","PB"]};
           CitePattern["isme"]=function(Lookin){
               let myname=["Barz","Avval"];
               return (myname.map(function(e){return Lookin.includes(e);})).reduce((p,c)=>p|c,false)? "isme":"co";
           };   
           let Pub=new Publications(Data.Publications,CitePattern);
           Pub.El.ToSHow();
           let El5=new Element({tag:"h2",Class:"SectionHeader",Text:"References"},References,["Element","main"]).ToSHow();

        }
        main();
        document.title=Data.Header.Name;