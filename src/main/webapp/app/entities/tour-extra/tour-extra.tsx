import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './tour-extra.reducer';

export const TourExtra = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const tourExtraList = useAppSelector(state => state.tourExtra.entities);
  const loading = useAppSelector(state => state.tourExtra.loading);
  const totalItems = useAppSelector(state => state.tourExtra.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="tour-extra-heading" data-cy="TourExtraHeading">
        <Translate contentKey="xploraAdminApp.tourExtra.home.title">Tour Extras</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="xploraAdminApp.tourExtra.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/tour-extra/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="xploraAdminApp.tourExtra.home.createLabel">Create new Tour Extra</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tourExtraList && tourExtraList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="xploraAdminApp.tourExtra.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="xploraAdminApp.tourExtra.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('enabled')}>
                  <Translate contentKey="xploraAdminApp.tourExtra.enabled">Enabled</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('enabled')} />
                </th>
                <th className="hand" onClick={sort('price')}>
                  <Translate contentKey="xploraAdminApp.tourExtra.price">Price</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('price')} />
                </th>
                <th className="hand" onClick={sort('shopProductId')}>
                  <Translate contentKey="xploraAdminApp.tourExtra.shopProductId">Shop Product Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('shopProductId')} />
                </th>
                <th className="hand" onClick={sort('shopUrl')}>
                  <Translate contentKey="xploraAdminApp.tourExtra.shopUrl">Shop Url</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('shopUrl')} />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="xploraAdminApp.tourExtra.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdDate')} />
                </th>
                <th className="hand" onClick={sort('defaultImage')}>
                  <Translate contentKey="xploraAdminApp.tourExtra.defaultImage">Default Image</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('defaultImage')} />
                </th>
                <th className="hand" onClick={sort('defaultImageData')}>
                  <Translate contentKey="xploraAdminApp.tourExtra.defaultImageData">Default Image Data</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('defaultImageData')} />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.tourExtra.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tourExtraList.map((tourExtra, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tour-extra/${tourExtra.id}`} color="link" size="sm">
                      {tourExtra.id}
                    </Button>
                  </td>
                  <td>{tourExtra.code}</td>
                  <td>{tourExtra.enabled ? 'true' : 'false'}</td>
                  <td>{tourExtra.price}</td>
                  <td>{tourExtra.shopProductId}</td>
                  <td>{tourExtra.shopUrl}</td>
                  <td>
                    {tourExtra.createdDate ? <TextFormat type="date" value={tourExtra.createdDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{tourExtra.defaultImage}</td>
                  <td>
                    {tourExtra.defaultImageData ? (
                      <div>
                        {tourExtra.defaultImageDataContentType ? (
                          <a onClick={openFile(tourExtra.defaultImageDataContentType, tourExtra.defaultImageData)}>
                            <img
                              src={`data:${tourExtra.defaultImageDataContentType};base64,${tourExtra.defaultImageData}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {tourExtra.defaultImageDataContentType}, {byteSize(tourExtra.defaultImageData)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{tourExtra.createdBy ? tourExtra.createdBy.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/tour-extra/${tourExtra.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/tour-extra/${tourExtra.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/tour-extra/${tourExtra.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="xploraAdminApp.tourExtra.home.notFound">No Tour Extras found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={tourExtraList && tourExtraList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default TourExtra;
