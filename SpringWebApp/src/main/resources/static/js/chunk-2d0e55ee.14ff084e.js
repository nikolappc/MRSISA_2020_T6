(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0e55ee"],{"93ca":function(a,e,l){"use strict";l.r(e);var n=function(){var a=this,e=a.$createElement,l=a._self._c||e;return l("v-form",{ref:"form",model:{value:a.valid,callback:function(e){a.valid=e},expression:"valid"}},[l("v-text-field",{attrs:{rules:a.nazivRules,label:"Naziv",required:""},model:{value:a.sala.naziv,callback:function(e){a.$set(a.sala,"naziv",e)},expression:"sala.naziv"}}),l("v-btn",{staticClass:"mr-4",attrs:{disabled:!a.valid,color:"success"},on:{click:a.dodajSalu}},[a._v(" Dodaj salu ")])],1)},t=[],s=l("bc3a"),o=l.n(s),i=l("a18c"),c={name:"AddSalu",data:function(){return{sala:{id:null,naziv:""},valid:!0,nazivRules:[function(a){return!!a||"Naziv je obavezno polje"}]}},methods:{dodajSalu:function(a){var e=this;a.preventDefault(),o.a.post("sala",this.sala).then((function(){e.$store.commit("setSnackbar",{text:"Sala je uspešno dodata",color:"success"}),i["a"].push("/sale")})).catch((function(a){console.log(a)}))}}},u=c,r=l("2877"),d=l("6544"),v=l.n(d),f=l("8336"),p=l("4bd4"),b=l("8654"),m=Object(r["a"])(u,n,t,!1,null,null,null);e["default"]=m.exports;v()(m,{VBtn:f["a"],VForm:p["a"],VTextField:b["a"]})}}]);
//# sourceMappingURL=chunk-2d0e55ee.14ff084e.js.map