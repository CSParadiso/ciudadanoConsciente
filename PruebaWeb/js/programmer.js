// HANDLE SUBSCRIPTION FORM

// Function to handle subscription form submission
async function handleSubscriptionFormSubmission(form) {
  // Collect values from the form and handle as needed
  const formData = new FormData(form);
  const jsonObject = Object.fromEntries(formData);

  // For demonstration purposes, log the form data to the console
  console.log('Form Data:', jsonObject);

  // Try to retrieve the file from GitHub
  try {
    const response = await fetch(`https://api.github.com/repos/${jsonObject.user}/${jsonObject.repo}/contents/${jsonObject.path}`, {
      headers: {
        'Accept': 'application/vnd.github.v3+json',
        'Authorization': 'Bearer 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' // TODO Borrar esto
      },
    });

    if (response.ok) {
      const responseData = await response.json();
      console.log('Files exist:', responseData);

      // Verify if files required are in the direcotory fetched
      const requiredFiles = ["readme.md", "model.json", "template.js", "thumbnail.png"];
      const existingFiles = responseData.filter(file => requiredFiles.includes(file.name));

      if (existingFiles.length === requiredFiles.length) {
        console.log('All required files exist.');
      } else {
        console.log('Some required files are missing.');
      }

    } else {
      console.error('Error checking files:', response.statusText);
    }
  } catch (error) {
    console.error('Error checking files:', error.message);
  }



}

// Attach the form submission event listener
document.addEventListener('DOMContentLoaded', function () {
  const form = document.getElementById('template-subscription-form');
  form.addEventListener('submit', function(event) {
    event.preventDefault();
    handleSubscriptionFormSubmission(form);
  });
});


