import React from 'react'
import { Container } from "react-bootstrap"

// The Parallax effect apply different speeds of the background and foreground in a div-parallax container.
const Parallax = () => {
  return (
    <div className="parallax mb-5">
			<Container className="text-center px-5 py-5 justify-content-center">
				<div className="animated-texts bounceIn">
					<h1>
						Experience the Best hospitality at <span className="hotel-color">Anonym Hotel</span>
					</h1>
					<h3>We offer the best services for all your needs.</h3>
				</div>
			</Container>
		</div>
  )
}

export default Parallax
