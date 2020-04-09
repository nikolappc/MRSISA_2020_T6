<template>
  <form @submit="dodajLekara" >
      <table class="table">
          <tr>
              <td>
                  ID
              </td>
              <td><input type="text" name="id"></td>
          </tr>
          <tr>
              <td>Ime</td>
              <td><input type="text" name="ime" /></td>
          </tr>
          <tr>
              <td>Prezime</td>
              <td><input type="text" name="prezime" /></td>
          </tr>
          <tr>
              <td>Korisničko ime</td>
              <td><input type="text" name="username"></td>
          </tr>
          <tr>
              <td>Lozinka</td>
              <td><input type="password" name="password"></td>
          </tr>
          <tr>
              <td>E-mail</td>
              <td><input type="text" name="email" /></td>
          </tr>
          <tr>
              <td>Adresa</td>
              <td><input type="text" name="adresa"></td>
          </tr>
          <tr>
              <td>Broj telefona</td>
              <td><input type="text" name="brojTelefona"></td>
          </tr>
          <tr>
              <td>JBO</td>
              <td><input type="text" name="jbo"></td>
          </tr>
          <tr>
              <td colspan="2" align='right'><input type="submit" value="Dodaj lekara"></td>
          </tr>
      </table>
  </form>
</template>

<script>
import axios from "axios";
export default {
    name: 'ProfilPacijenta',
    methods: {
        dodajLekara: (event) => {
            event.preventDefault();

            let forma = document.getElementsByTagName('form')[0];
            
            var object = {};
            
            forma.forEach(function(input){
                if(input.type != "submit")
                    object[input.name] = input.value;
            });
            
            axios
            .post('lekar',object)
            .then(response => {
                console.log(response);
            })
            .catch(function (error) { console.log(error); });
        }
    }
}
</script>
