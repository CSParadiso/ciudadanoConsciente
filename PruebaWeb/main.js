document.addEventListener('DOMContentLoaded', function () {
  // DataTables initialization
  $('#userTable').DataTable();
  $('#voteTable').DataTable();
});

function fetchUsers() {
  fetch('http:localhost:3705/users') // Replace with the actual API base URL
      .then(response => response.json())
      .then(data => {
          displayDataInTable(data, 'userTable');
      })
      .catch(error => {
          console.error('Error fetching users:', error);
      });
}

function fetchVotes() {
  fetch('http:localhost:3705/votes') // Replace with the actual API base URL
      .then(response => response.json())
      .then(data => {
          displayDataInTable(data, 'voteTable');
      })
      .catch(error => {
          console.error('Error fetching votes:', error);
      });
}

function showUsersTable() {
  fetchUsers();
  $('#userTable').show();
}

function showVotesTable() {
  fetchVotes();
  $('#voteTable').show();
}

function displayDataInTable(data, tableId) {
  const table = $(`#${tableId}`).DataTable();
  table.clear().draw();
  
  data.forEach(row => {
      table.row.add(Object.values(row));
  });

  table.draw();
}
