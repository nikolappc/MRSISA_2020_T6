(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d217152"],{c4e0:function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("v-form",{ref:"form",model:{value:e.valid,callback:function(t){e.valid=t},expression:"valid"}},[a("v-datetime-picker",{attrs:{label:"Početak termina",dateFormat:"dd.MM.yyyy"},model:{value:e.pregled.termin.pocetak,callback:function(t){e.$set(e.pregled.termin,"pocetak",t)},expression:"pregled.termin.pocetak"}},[a("template",{slot:"dateIcon"},[a("v-icon",[e._v("mdi-calendar")])],1),a("template",{slot:"timeIcon"},[a("v-icon",[e._v("mdi-clock-outline")])],1)],2),a("v-datetime-picker",{attrs:{label:"Kraj termina",dateFormat:"dd.MM.yyyy"},model:{value:e.pregled.termin.kraj,callback:function(t){e.$set(e.pregled.termin,"kraj",t)},expression:"pregled.termin.kraj"}},[a("template",{slot:"dateIcon"},[a("v-icon",[e._v("mdi-calendar")])],1),a("template",{slot:"timeIcon"},[a("v-icon",[e._v("mdi-clock-outline")])],1)],2),a("v-select",{attrs:{items:e.lekari,label:"Lekar",dense:"",rules:e.rule,outlined:"",required:"","return-object":""},scopedSlots:e._u([{key:"selection",fn:function(t){return[e._v(" "+e._s(t.item.ime)+" "+e._s(t.item.prezime)+" ")]}},{key:"item",fn:function(t){return[e._v(" "+e._s(t.item.ime)+" "+e._s(t.item.prezime)+" ")]}}]),model:{value:e.pregled.lekar,callback:function(t){e.$set(e.pregled,"lekar",t)},expression:"pregled.lekar"}}),a("v-select",{attrs:{items:e.sale,label:"Sala",outlined:"",dense:"",rules:e.rule,required:"","item-text":"naziv","return-object":""},model:{value:e.pregled.sala,callback:function(t){e.$set(e.pregled,"sala",t)},expression:"pregled.sala"}}),a("v-select",{attrs:{items:e.tipovi,label:"Tip",outlined:"",dense:"",rules:e.rule,required:"","return-object":""},scopedSlots:e._u([{key:"selection",fn:function(t){return[e._v(" Naziv: "+e._s(t.item.naziv)+" , Cena: "+e._s(t.item.stavkaCenovnika.cena)+" ")]}},{key:"item",fn:function(t){return[e._v(" Naziv: "+e._s(t.item.naziv)+" , Cena: "+e._s(t.item.stavkaCenovnika.cena)+" ")]}}]),model:{value:e.pregled.tipPosete,callback:function(t){e.$set(e.pregled,"tipPosete",t)},expression:"pregled.tipPosete"}}),a("v-btn",{staticClass:"mr-4",attrs:{disabled:!e.valid,color:"success"},on:{click:e.dodajTermin}},[e._v(" Dodaj termin ")])],1)},l=[],i=a("bc3a"),o=a.n(i),r=a("a18c"),c={name:"AddTermin",data:function(){return{pregled:{termin:{pocetak:"",kraj:""},tipPosete:null,sala:null,lekar:null},tipovi:[],sale:[],lekari:[],valid:!0,rule:[function(e){return!!e||"Obavezno polje"}]}},mounted:function(){var e=this;o.a.get("tip").then((function(t){e.tipovi=t.data,console.log(t)})).catch((function(){e.tipovi=[{naziv:"pera",tip:"operacija",stavkaCenovnika:{cena:20}}]})),o.a.get("sala").then((function(t){e.sale=t.data,console.log(t)})).catch((function(){e.sale=[{id:"1",naziv:"Neka klinika"}]})),o.a.get("lekar").then((function(t){e.lekari=t.data,console.log(t)})).catch((function(){e.lekari=[{ime:"pera",prezime:"neko"}]}))},methods:{dodajTermin:function(e){var t=this;e.preventDefault(),o.a.post("poseta",this.pregled).then((function(){t.$store.commit("setSnackbar",{text:"Uspešno ste dodali slobodan termin",color:"success"}),r["a"].push("/sale")})).catch((function(e){console.log(e)}))}}},s=c,d=a("2877"),u=a("6544"),m=a.n(u),p=a("8336"),v=a("4bd4"),k=a("132d"),f=a("b974"),b=Object(d["a"])(s,n,l,!1,null,null,null);t["default"]=b.exports;m()(b,{VBtn:p["a"],VForm:v["a"],VIcon:k["a"],VSelect:f["a"]})}}]);
//# sourceMappingURL=chunk-2d217152.0135d093.js.map