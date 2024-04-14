import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TourCategory from './tour-category';
import TourCategoryDetail from './tour-category-detail';
import TourCategoryUpdate from './tour-category-update';
import TourCategoryDeleteDialog from './tour-category-delete-dialog';

const TourCategoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TourCategory />} />
    <Route path="new" element={<TourCategoryUpdate />} />
    <Route path=":id">
      <Route index element={<TourCategoryDetail />} />
      <Route path="edit" element={<TourCategoryUpdate />} />
      <Route path="delete" element={<TourCategoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TourCategoryRoutes;
