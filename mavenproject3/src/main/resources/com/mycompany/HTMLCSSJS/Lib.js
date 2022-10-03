
    
        class Element{    
            constructor(HeaderContent,Content,classname){
                this.HC=HeaderContent; 
                this.C=Content;
                this.DIV=document.createElement("div");
                classname.push("Element");
                classname.forEach((item)=>this.DIV.classList.add(item));
                this.Header=this.HeaderCreate(this.HC);
                this.SignP=document.createElement("p");
                this.SignP.classList.add("plus");
                this.SignP.appendChild(document.createTextNode("+"));
                this.DIV.appendChild(this.SignP);
                this.DIV.appendChild(this.Header);        
                this.ContentHolder=this.ContentCreate(Content);
                try{
                this.DIV.appendChild(this.ContentHolder); 
                }catch(err){
                    console.log(err.length);
                }
                this.Header.toHide=this.ContentHolder;
                this.Header.toChange=this.SignP;
                this.Header.addEventListener('click',function(){
                    if (this.toHide.id=='toShow'){
                        this.toHide.id='toHide';
                        this.toChange.innerHTML="(+)";
                    }else{
                        this.toHide.id='toShow';
                        this.toChange.innerHTML="(-)";
                    }
                });  
            }
            HeaderCreate(HeaderContent){
                var H=document.createElement(HeaderContent.tag);
                H.setAttribute("class",HeaderContent.Class);
                H.appendChild(document.createTextNode(HeaderContent.Text)); 
                return H;        
            }
            ContentCreate(CONTENT){
                var CellArray=[];
                function creator(Content){
                    var Cel;
                    if (typeof(Content)===typeof("String")){
                        Cel=document.createElement("div");
                        var pa=document.createElement("p");
                        pa.appendChild(document.createTextNode(Content));
                        Cel.appendChild(pa);
                        Cel.style.display='none';
                    }else if (Content instanceof Element){               
                        Cel=Content.DIV;
                    }else{
                        Cel=Content.El;
                    }
                    CellArray.push(Cel);
                }      
                if ((Array.isArray(CONTENT))&&(typeof(CONTENT)!==typeof("String"))){
                    CONTENT.forEach(creator);        
                    var holder=document.createElement("div");
                    CellArray.forEach((item)=>holder.appendChild(item));
                    return holder;
                }else{
                    creator(CONTENT);
                    return CellArray[0];
                }
            }   
            ToSHow(){
                document.body.appendChild(this.DIV);
                this.DIV.style.display='block';
            }    
        }
        class CustomElement{
            constructor(tag,text,attribs){
                if (tag!==""){
                    this.El=document.createElement(tag);
                    this.El.innerHTML=text;
                    for (var x in attribs){
                        if (x==="class"){
                            try{
                                attribs[x].forEach((item)=>this.El.classList.add(item));
                            }catch(error){
                                this.El.setAttribute(x,attribs[x]);
                            }
                        }else{
                            this.El.setAttribute(x,attribs[x]);
                        }                    
                    }
                }else{
                    this.El=null;
                } 
            }
            appendAll(ELs){
                ELs.forEach((item)=>this.appender(item));        
            }
            appender(item){
                try{
                if (item instanceof CustomElement){
                    this.El.appendChild(item.El);
                }else{
                    this.El.appendChild(item.DIV);            
                }
                }catch(err){
                    console.log(err);
                }
            }

            ToShow(){
                document.body.appendChild(this.El);
            }
            Clone(){
                var ClonCustom=new CustomElement("","","");        
                ClonCustom.El=this.El.cloneNode(true);
                return ClonCustom;
            }
        }
        class Header{
            constructor(DATA){
                this.Name=DATA.Name;
                this.Discription=DATA.Discription;
                this.Info=DATA.Info;        
                let Name=new CustomElement("h1",this.Name,{"id":"Name"});
                let Dis=new CustomElement("p",this.Discription,{"id":"Discription"});
                let Infos=this.Info.map(function(e){return (new IconData(e)).Create();});
                let infoDiv=new CustomElement("div","",{"class":"Infos"});
                infoDiv.appendAll(Infos);
                this.Div=new CustomElement("div","",{"class":"Header"});
                this.Div.appendAll([Name,Dis,infoDiv]);
                }
                Create(){
                    return this.Div.El;
                }
                ToSHow(){
                document.body.appendChild(this.Div.El);
                }    
        }
        class Profile{
            constructor(DATA){
                this.Photo=DATA.Photo;
                this.Age=DATA.Age;
                this.MaterialStatus=DATA.MaterialStatus;
                this.Profile=DATA.Profile;
                this.Comment=DATA.Comment;        
                let Photo=new CustomElement("img","",{"class":"Photo","src":this.Photo});
                let Age=new TitleContent("Age:",this.Age,"Age").Create();
                let MaterialStatus=new TitleContent("MaterialStatus:",this.MaterialStatus,"MaterialStatus").Create();
                let Profile=new TitleContent("Profile:",this.Profile,"Profile").Create();        
                let Comment=new CustomElement("p",this.Comment,{"class":"Comment"});
                let Edmore=new Element({tag:"h3",Text:"more",Class:"SectionHeader"},[Comment],["Element",Comment?"more":"Broken"]);
                let DivImage=new CustomElement("div","",{"class":"ProfileImage"}); 
                DivImage.appender(Photo);
                let DivData=new CustomElement("div","",{"class":"ProfileData"});
                DivData.appendAll([Age,MaterialStatus,Profile,Edmore]);
                this.Div=new CustomElement("div","",{"class":"ProfileContent"});
                this.Div.appendAll([DivImage,DivData]);        
            }

            Create(){
               return this.Div;
            }
        }
        class WorkExp{
            constructor(EData){
                this.Company=EData.Company;        
                this.Position=EData.Position;
                this.Projects=EData.Projects;
                this.StartDate=EData.StartDate;
                this.EndDate=EData.EndDate;
                this.Responsibilities=EData.Responsibilities;
                this.Achievements=EData.Achievements;
                let Projectlist=[];
                let ResList=[];
                let AchiList=[];
                let Content=new CustomElement("div","",{"class":"WorkExperience"});
                if (this.Projects !=null){
                try{
                    this.Projects.forEach((item)=>Projectlist.push(this.CreateProject(item)));
                }catch(err){
                    Projectlist.push(CreateProject(this.Projects));
                }
                    let CP=new CustomElement("div","",{"class":"Projects"})
                            CP.appender(new CustomElement("h3","Projects:",{}));
                    CP.appendAll(Projectlist);
                    Content.appender(CP);
                }
                if (this.Responsibilities !=null){
                    let RL=this.CreateList(this.Responsibilities);
                    let RLHead= new CustomElement("h3","Responsibilities:",{});
                    let RLdiv=new CustomElement("div","",{"class":"Responsibilities"});
                    RLdiv.appendAll([RLHead,RL]);
                    Content.appender(RLdiv);
                }
                if (this.Achievements !=null){
                    let AV=this.CreateList(this.Achievements);
                    let AVHead=new CustomElement("h3","Achivements:",{});        
                    let AVdiv=new CustomElement("div","",{"class":"Achivements"});
                    AVdiv.appendAll([AVHead,AV]);
                    Content.appender(AVdiv);
                }
                this.WORKEXP=new Element({tag:"h3",Text:this.Company+Dates(this),Class:"SectionHeader"},Content,["Company"]);
            }
            CreateProject(Proj){
                var linkclass;
                ((Proj.Link == null)|(Proj.Link=="")) ? linkclass=["ProjectsLink","Broken"]:linkclass="ProjectsLink";
                let Plink=new CustomElement("a","Visit the link to find out more",{"href":Proj.Link,"class":linkclass});
                let PComment=new CustomElement("p",Proj.Comment,{"id":"comment","class":"Comment"});
                return new Element({tag:"h3",Text:Proj.Title,Class:"SectionHeader"},[PComment,Plink],["Element","Project"]);        
            }
            CreateList(Data){
                let RL=new CustomElement("ol","",{});
                try{
                    Data.forEach((item)=>RL.appender(new CustomElement("li",item,{})));
                }catch(err){
                    RL.appender(new CustomElement("li",Data,{}));
                }
                return RL;
            }
            Create(){
                return this.WORKEXP;
            }    
        }
        class Education{
            constructor(EData){
                this.Institue=EData.Institue;
                this.Degree=EData.Degree;
                this.InstitueLink=EData.Link;
                this.StartDate=EData.StartDate;
                this.EndDate=EData.EndDate;
                this.Thesis=EData.Thesis;
                this.CGPA=EData.CGPA;
                this.Comment=EData.Comment;
                this.EdHeader=new CustomElement("h3",this.Degree+Dates(this),{"class":"EdHeader"});
                this.EdInstitue=new CustomElement("a",this.Institue,{"href":this.InstitueLink,"class":"EdLink"});
                this.pCGPA=new CustomElement("p","<b>CGPA<b>:"+this.CGPA,{"class":["more","Thesis"]});
                this.pThesis=new CustomElement("p","<b>Thesis<b>:"+this.Thesis,{"class":["more","CGPA"]});
                this.pComment=new CustomElement("p",this.Comment,{"id":"comment","class":["more","Comment"]});
                this.Edmore=new Element({tag:"h3",Text:"more",Class:"SectionHeader"},[this.pCGPA,this.pThesis,this.pComment],["Element","more"]);        
            }
            Create(){
                var div=new CustomElement("div","",{"class":"EducationContent"});
                div.appendAll([this.EdHeader,this.EdInstitue,this.Edmore]);
                return div;
            }    
        }
        class Skill{
            constructor(EData){
                let Rows=EData.map(this.tablerow);
                this.table=new CustomElement("table","",{"class":"SkillTable"});
                this.table.appendAll(Rows);
            }
            tablerow(EData){        
                let Star="";
                for(let i=0;i<10;i++){ 
                    (i<EData.Rate)?Star+="&#9733":Star+="&#9734";};
                let dSkill=new CustomElement("td",EData.Skill,{"class":"Skill"});
                let dStar=new CustomElement("td",Star,{"class":"Stars"});
                let drow=new CustomElement("tr","",{});
                drow.appendAll([dSkill,dStar]);
                return drow;
            }
            Create(){   
                var div=new CustomElement("div","",{"class":"SkillContent"});
                div.appender(this.table);
                return div;
            }
        }
        class Language{
            constructor(EData){
               this.Language=EData.Language;
               this.Fluency=EData.Fluency;
               this.Test=EData.Test;
               this.Score=EData.Score;
               this.Comment=EData.Comment;
               let cComment=["more","Comment"];
               let cScore=["Score","more"];
               let cmore=["more"];
               let cTest=["more","Test"];
               if ((this.Comment=="")||(this.Comment==null)) {cComment.push("Broken");};
               if ((this.Score=="")||(this.Score==null)) {cScore.push("Broken");};
               if ((cScore.length==3)&&(cComment.length==3)) {cmore.push("Broken");}; 
               cTest.push(cScore[cScore.length-1]);
               this.pLanguage=new CustomElement("p",this.Language+":",{"class":"Language"});
               this.pFluency=new CustomElement("p",this.Fluency,{"class":"Fluency"});
               this.pTest=new CustomElement("p",this.Test,{"class":cScore});
               this.pScore=new CustomElement("p"," Score:"+this.Score,{"class":cTest});
               this.pComment=new CustomElement("p",this.Comment,{"class":cComment});
               this.Lanmore=new Element({tag:"h3",Text:"more",Class:["SectionHeader"]},[this.pTest,this.pScore,this.pComment],cmore);
            }
            Create(){   
                var div=new CustomElement("div","",{"class":"LanguageContent"});
                div.appendAll([this.pLanguage,this.pFluency,this.Lanmore]);
                return div; 
            }
        }
        class Certificate{
            constructor(){

            }
        }
        class Awards{
            constructor(){

            }
        }
        class Reference{
            constructor(DATA){
                this.Name=DATA.Name;
                this.Position=DATA.Position;
                this.Company=DATA.Company;
                this.Phone=DATA.Phone;
                this.Email=DATA.Email;
                this.Comment=DATA.Comment;
                let Name=new CustomElement("p",this.Name,{"class":"Name"});
                let Position=new CustomElement("p",this.Position,{"class":"Position"});
                let Company=new CustomElement("p",this.Company,{"class":"Company"});
                let Phone=new CustomElement("p",this.Phone,{"class":"Phone"});
                let Email=new CustomElement("p",this.Email,{"class":"Email"});
                let Comment=new CustomElement("p",this.Comment,{"class":"Comment"});        
                let Edmore=new Element({tag:"h3",Text:"more",Class:"SectionHeader"},[Comment],["Element","more"]);
                this.Div=new CustomElement("div","",{"class":"ReferenceContent"});
                this.Div.appendAll([Name,Position,Company,Phone,Email,Edmore]);
            }
            Create(){
                return this.Div;
            }
        }
        class Publications{
            constructor(Data,cp){
                this.els=new Set();
                this.CP=cp;
                Data.forEach((item)=>this.CiteCreate(item));
                let Texts={"JOUR":"Journals","BOOK":"Books","CHAP":"Book Chapters","CONF":"Conference"};
                let ElementArray=[];

                this.els.forEach(function(e){ElementArray.push( new Element({tag:"h3",Class:"SectionHeader",Text:Texts[e]},this[e],["Element",e]));}.bind(this));

                try{
                this.El=new Element({tag:"h2",Class:"SectionHeader",Text:"Publications"},ElementArray,["Element",ElementArray.length?"main":"Broken"]);
            }catch(err){
                console.log(err);
            }
            } 
            CiteCreate(DATA){
                let Div=new CustomElement("li","",{"class":["Cite",DATA["Type"]]});
                let checker=this.CP["isme"];
                let dim=Array.from(this.CP[DATA["Type"]]).pop();
                this.CP[DATA["Type"]].forEach(function(d){

                    if(d!="Authors"){
                        if (d==dim){
                            if (DATA[d]===undefined){ 
                                Div.appendAll([new CustomElement("span","",{"class":[d]}),new CustomElement("span",'.&nbsp',{"class":["sep"]})]);
                            }else{
                                Div.appendAll([new CustomElement("span",DATA[d],{"class":[d]}),new CustomElement("span",'.&nbsp',{"class":["sep"]})]);
                            }
                        }else{
                            if (DATA[d]!==undefined) Div.appendAll([new CustomElement("span",DATA[d],{"class":[d]}),new CustomElement("span",',&nbsp',{"class":["sep"]})]);



                        }
                    }else{    
                        try{
                        DATA[d].forEach(function(Au){Div.appendAll([new CustomElement("span",Au.replace(',',''),{"class":[d,checker(Au)]}),new CustomElement("span",',&nbsp',{"class":["sep"]})]);});
                    }catch(err){
                        console.log(err);
                    }
                    }
                });
                try{
                (this[DATA["Type"]])?this[DATA["Type"]].push(Div):this[DATA["Type"]]=[Div];
                this.els.add(DATA["Type"]);
            }catch(err){
                console.log(err);
            }
            }
        }
        class TitleContent{
            constructor(Title,Content,Class){
                this.P=new CustomElement('p',"",{"class":Class});
                this.P.appendAll([new CustomElement('span',Title,{"class":"Title"}),new CustomElement('span',Content,{"class":"Content"})]);
            }
            Create(){
                return this.P;
            }
        }
        class IconData{
            constructor(DATA){
               this.div=new CustomElement("div","",{"class":"InfoContainer"});
               let icon=new CustomElement("img","",{"src":DATA.icon});
               let content=null;
               if (DATA['Link']){
                  content=new CustomElement("a",DATA.Value,{"id":DATA.Name,"href":DATA.Link,"target":"_blank"}); 
              }else{
                  content=new CustomElement("p",DATA.Value,{"id":DATA.Name}); 
              }
                this.div.appendAll([icon,content]);
            }
            Create(){
                return this.div;
            }
        }
        function Dates(DATA){
            return " ("+DATA.StartDate+"-"+DATA.EndDate+")";
        }
        function Work(DATA){
           let WExps=new CustomElement("div","",{"class":"Position"});
           console.log(DATA.Position);
           WExps.appender(new CustomElement("h3",DATA.Position));
           DATA.Companies.forEach((item)=>WExps.appender((new WorkExp(item)).Create()));
           return WExps;   
        }

