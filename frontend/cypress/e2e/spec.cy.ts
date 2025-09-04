describe('My First Test', () => {
  it('Visits the initial project page', () => {
    cy.visit('/')
    cy.contains('NCI EVS Report Writer 2')
  })
})
