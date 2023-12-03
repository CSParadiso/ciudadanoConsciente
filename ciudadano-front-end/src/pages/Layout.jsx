import { AppBar, Box, Button, IconButton, Toolbar, Typography } from "@mui/material";
import { Outlet, Link } from "react-router-dom";
import MenuIcon from "@mui/icons-material/Menu"
const Layout = () => {
  return (
      <Box >
        <AppBar>
        <Toolbar>
            <IconButton
              size="large"
              edge="start"
              color="inherit"
              aria-label="menu"
              sx={{ mr: 2 }}
            >
              <MenuIcon />
            </IconButton>
            <Link to="/">
              <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                Home
              </Typography>
            </Link>
            <Button color="inherit">Login</Button>
          </Toolbar>
        </AppBar>
        <Outlet />
       </Box>
  )
};

export default Layout;