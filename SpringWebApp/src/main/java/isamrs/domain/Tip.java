package isamrs.domain;

public enum Tip {
   operacija("operacija"),
   pregled("pregled");

   String tip;
   Tip(String pregled) {
      this.tip = pregled;
   }
}