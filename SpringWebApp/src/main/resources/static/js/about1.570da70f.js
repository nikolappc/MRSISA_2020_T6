(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["about1"],{de90:function(a,t,o){"use strict";o.r(t);var n=function(){var a=this,t=a.$createElement,o=a._self._c||t;return o("div",{staticClass:"profilPacijenta"},[o("v-container",[o("v-card",[o("v-simple-table",[o("thead",[o("th",[a._v("Moj profil")]),o("th",[a._v(a._s(a.ulogovani.email))])]),o("tbody",[o("tr",[o("td",[a._v("Lozinka:")]),o("td",[a._v(a._s(a.ulogovani.password))])]),o("tr",[o("td",[a._v("Ime:")]),o("td",[a._v(a._s(a.ulogovani.ime))])]),o("tr",[o("td",[a._v("Prezime:")]),o("td",[a._v(a._s(a.ulogovani.prezime))])]),o("tr",[o("td",[a._v("Adresa:")]),o("td",[a._v(a._s(a.toStringAdresa(a.ulogovani)))])]),o("tr",[o("td",[a._v("Telefon:")]),o("td",[a._v(a._s(a.ulogovani.brojTelefona))])]),o("tr",[o("td",[a._v("Jedinstveni broj osiguranika:")]),o("td",[a._v(a._s(a.ulogovani.jbo))])])])]),o("v-btn",{staticClass:"blue",attrs:{slot:"action",to:{path:"profilPacijentaIzmjena"},dark:"",medium:"",left:""},slot:"action"},[a._v("Izmjena profila")])],1)],1)],1)},e=[],i=o("a18c"),r={name:"ProfilPacijenta",data:function(){return{ulogovani:{}}},methods:{toStringAdresa:function(a){return a.adresa.adresa+", "+a.adresa.grad+", "+a.adresa.drzava}},mounted:function(){this.ulogovani=this.$store.state.ulogovan,""==this.ulogovani&&i["a"].push("/")}},s=r,l=o("2877"),d=o("6544"),v=o.n(d),u=o("8336"),_=o("b0af"),c=o("a523"),f=o("1f4f"),p=Object(l["a"])(s,n,e,!1,null,null,null);t["default"]=p.exports;v()(p,{VBtn:u["a"],VCard:_["a"],VContainer:c["a"],VSimpleTable:f["a"]})}}]);
//# sourceMappingURL=about1.570da70f.js.map