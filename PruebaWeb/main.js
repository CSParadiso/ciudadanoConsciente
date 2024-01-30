// HANDLE SUBSCRIPTION FORM

// Function to handle subscription form submission
async function handleSubscriptionFormSubmission(form) {
  // Collect values from the form and handle as needed
  const formData = new FormData(form);
  const jsonObject = Object.fromEntries(formData);

  // For demonstration purposes, log the form data to the console
  console.log('Form Data:', jsonObject);

  // Verify if the files exist in GitHub
  try {
    const response = await fetch(`https://api.github.com/repos/${jsonObject.user}/${jsonObject.repo}/contents/${jsonObject.path}`, {
      headers: {
        'Accept': 'application/vnd.github.v3+json',
      },
    });

    if (response.ok) {
      const responseData = await response.json();
      console.log('Files exist:', responseData);
    } else {
      console.error('Error checking files:', response.statusText);
    }
  } catch (error) {
    console.error('Error checking files:', error.message);
  }

}

// Attach the form submission event listener
document.addEventListener('DOMContentLoaded', function () {
  const form = document.getElementById('subscription-form');
  form.addEventListener('submit', function(event) {
    event.preventDefault();
    handleSubscriptionFormSubmission(form);
  });
});


