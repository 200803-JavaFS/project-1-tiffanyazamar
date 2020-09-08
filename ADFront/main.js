const url = "http://localhost:8081/project0/";

document.getElementById("loginbtn").addEventListener("click", loginFunc);

var loggedInUser;

async function filterReimbursement(selectObject) {
	  var value = selectObject.value;  
	  findAllFunc(value);
	}

async function loginFunc() {
	  document.getElementById("login-row").style.display = "none";
	  document.getElementById("spinner-row").style.display = "block";

  let usern = document.getElementById("username").value;
  let userp = document.getElementById("password").value;

  let user = {
    username: usern,
    password: userp,
  };

  let resp = await fetch(url + "login", {
    method: "POST",
    body: JSON.stringify(user),
    credentials: "include",
    // mode: "no-cors"
  });


  if (resp.status === 200) {
    loggedInUser = await resp.json();
    document.getElementById("spinner-row").style.display = "none";
    let welcomeSpan = document.createElement("h2");
    welcomeSpan.innerText = "Welcome Back " + loggedInUser.firstName + "!!!";
    document.getElementById("welcome-message").appendChild(welcomeSpan);
    let logoutButton = document.createElement("button");
    logoutButton.className = "btn btn-danger";
    logoutButton.id = "logout-button";
    logoutButton.innerText = "Logout";
    logoutButton.onclick = logout;
    document.getElementById("logout").appendChild(logoutButton);
    
    
    
    
    document.getElementById("page-body").style.display = "block";

    findAllFunc('All');
  } else {
	  document.getElementById("login-row").style.display = "block";
	  document.getElementById("spinner-row").style.display = "none";
    document.getElementById("login-message").innerText = "Incorrect username or password. Please try again!!";
  }
}

function showAddButton(){
	document.getElementById("new-reimbursement").style.display = "block";
	document.getElementById("new-reimbursement-button").style.display = "none";

}
async function findAllFunc(status) {
  document.getElementById("avbody").innerText = "";

  let resp = await fetch(url + "reimbursement/status/" + status , {
    credentials: "include",
  });

  if (resp.status === 200) {
    let data = await resp.json();
    for (let reimbursement of data) {
      let row = document.createElement("tr");
      if(reimbursement.status.statusName==='Pending'){
    	  row.className = "row-pending";
      }else if(reimbursement.status.statusName==='Denied'){
    	  row.className = "row-denied";
      }else if(reimbursement.status.statusName==='Approved'){
    	  row.className = "row-approved";
      }
      let cell = document.createElement("td");
      cell.innerHTML = reimbursement.id;
      row.appendChild(cell);
      let cell2 = document.createElement("td");
      cell2.innerHTML = "$" + reimbursement.amount;
      row.appendChild(cell2);
      let cell3 = document.createElement("td");
      cell3.innerHTML = reimbursement.type.typeName;
      row.appendChild(cell3);
      let cell4 = document.createElement("td");
      cell4.innerHTML = reimbursement.description;
      row.appendChild(cell4);
      let cell5 = document.createElement("td");
      cell5.innerHTML = reimbursement.status.statusName;
      row.appendChild(cell5);
      let cell6 = document.createElement("td");
      cell6.innerHTML =
        reimbursement.author.firstName + " " + reimbursement.author.lastName;
      row.appendChild(cell6);
      const current_datetime = new Date(reimbursement.submitted);
      const formatted_date =
        current_datetime.getFullYear() +
        "-" +
        (current_datetime.getMonth() + 1) +
        "-" +
        current_datetime.getDate() +
        " " +
        current_datetime.getHours() +
        ":" +
        current_datetime.getMinutes() +
        ":" +
        current_datetime.getSeconds();

      let cell7 = document.createElement("td");
      cell7.innerHTML = formatted_date;
      row.appendChild(cell7);
      let resolver = "N/A";
      if (reimbursement.resolver != null) {
        resolver =
          reimbursement.resolver.firstName +
          " " +
          reimbursement.resolver.lastName;
      }
      let cell8 = document.createElement("td");
      cell8.innerHTML = resolver;
      row.appendChild(cell8);

      let resolved_date = "N/A";
      if (reimbursement.resolved != null) {
        const resolved_datetime = new Date(reimbursement.resolved);
        resolved_date =
        	resolved_datetime.getFullYear() +
          "-" +
          (resolved_datetime.getMonth() + 1) +
          "-" +
          resolved_datetime.getDate() +
          " " +
          resolved_datetime.getHours() +
          ":" +
          resolved_datetime.getMinutes() +
          ":" +
          resolved_datetime.getSeconds();
      }
      let cell9 = document.createElement("td");
      cell9.innerHTML = resolved_date;
      row.appendChild(cell9);
      
      let cell10 = document.createElement("td");

      let approveButton = "";
      let deniedButton = "";
      if(loggedInUser.userRole.roleName ==='Finance Manager' && (reimbursement.status.statusName==='Pending' || reimbursement.status.statusName==='Denied')){
    	  approveButton = document.createElement("button");
          let approvedIcon = document.createElement("i");
          approvedIcon.className ="fa fa-check";
          approveButton.appendChild(approvedIcon);
          approveButton.className = "btn btn-success";
          approveButton.id = "approve-" + reimbursement.id;
          approveButton.onclick = function(){
        	  approveReimbursement(reimbursement.id);
          }
          cell10.appendChild(approveButton);

          
          if(reimbursement.status.statusName==='Pending'){
        	  deniedButton = document.createElement("button");
              let deniedIcon = document.createElement("i");
              deniedIcon.className ="fa fa-times";
              deniedButton.appendChild(deniedIcon);
              deniedButton.className = "btn btn-danger";
              deniedButton.id = "denied-" + reimbursement.id;
              deniedButton.onclick = function(){
            	  deniedReimbursement(reimbursement.id);
              }
              cell10.appendChild(deniedButton);
          }
      }
    	  
    	       
      


      row.appendChild(cell10);
      document.getElementById("avbody").appendChild(row);
    }
  }
}

async function logout(){
	  let resp = await fetch(url + "logout", {
		    method: "POST",
		    credentials: "include",
		    // mode: "no-cors"
		  });
	  if(resp.status===200){
		  location.reload();
	  }
}
async function AddFunc() {
  let type = document.getElementById("type").value;
  let amount = document.getElementById("amount").value;
  let description = document.getElementById("description").value;

  let reimbursement = {
    type: type,
    amount: amount,
    description: description,
  };

  console.log(reimbursement);

  let resp = await fetch(url + "reimbursement", {
    method: "POST",
    body: JSON.stringify(reimbursement),
    credentials: "include",
  });

  if (resp.status === 201) {
	  document.getElementById("message").innerText =
	      "Successfully created a new reimbursement.";
	  
	  document.getElementById("new-reimbursement").style.display = "none";
		document.getElementById("new-reimbursement-button").style.display = "block";

	  document.getElementById("type").value="";
	  document.getElementById("amount").value="";
	  document.getElementById("description").value="";

    findAllFunc('All');
  } else {
    document.getElementById("message").innerText =
      "Failed to add new reimbursement";
  }
}

async function approveReimbursement(id) {


	  let resp = await fetch(url + "reimbursement/update/approve", {
	    method: "POST",
	    body: id,
	    credentials: "include",
	  });

	  if (resp.status === 201) {
	    findAllFunc('All');
	  } else {
	    document.getElementById("login-row").innerText =
	      "Avenger could not be added.";
	  }
	}
	
	
	async function deniedReimbursement(id) {
		  let resp = await fetch(url + "reimbursement/update/denied", {
		    method: "POST",
		    body: JSON.stringify(id),
		    credentials: "include",
		  });

		  if (resp.status === 201) {
		    findAllFunc('All');
		  } else {
		    document.getElementById("login-row").innerText =
		      "Avenger could not be added.";
		  }
		}